package ied;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class MainProgram {
	
	public MovieInfo searchByName(String movie, String lang) {
		MovieInfo mi = null;
		JDBCJob jdbc = new JDBCJob();
		XPathQuery xq = new XPathQuery();
		LinkedMovieJob l = new LinkedMovieJob();
		try {
			if (jdbc.readDataBase(movie) != null) {
				mi = new MovieInfo(); //Create if only movie exists
				mi = jdbc.readDataBase(movie);
			}
		} catch (Exception e) {
			
		}
		if (mi != null) { //Check if movie exists
			xq.getXPathResult(mi, movie);
			l.producerResult(mi, l.queryResults(l.producerQuery(movie, lang)));
			l.directorResult(mi, l.queryResults(l.directorQuery(movie, lang)));
			l.actorResult(mi, l.queryResults(l.actorQuery(movie, lang)));
		}
		return mi;
	}
	
	public void searchByActor(String actor, String lang) {
		ArrayList<String> movieResults = new ArrayList<>();
		LinkedMovieJob l = new LinkedMovieJob();
		movieResults = l.movieResult(l.queryResults(l.movieQuery(actor, lang)));
		if (movieResults != null && !movieResults.isEmpty()) {
			for (String m : movieResults) {
				m = m.replaceAll("@.*", "");
				System.out.println("Film played: " + m);
				MovieInfo mi = null;
				mi = searchByName(m, lang);
				if (mi == null) {
					System.out.println("We do not have any information about the movie " +
				m + "\n");
				} else {
					showInfoMovie(mi);
				}
			}
		} else {
			System.out.println("We don't have any film for this actor\n");
		}
		
	}
	
	public void showInfoMovie(MovieInfo mi) {
		String movieName, releaseDate, distributor, genre, domesticGross,
		worldwideGross, budget, plot;
		
		if (mi.getMovie() != null) { //Title
			movieName = mi.getMovie();
		} else {
			movieName = "Information Not Available";
		}
		System.out.println("Movie: " + movieName);
		
		if (mi.getReleaseDate() != null) {
			releaseDate = mi.getReleaseDate().toString();
		} else {
			releaseDate = "Release Date Not Available";
		}
		System.out.println("Release Date: " + releaseDate);
		
		if (mi.getGenre() != null) {
			genre = mi.getGenre();
		} else {
			genre = "Genre Not Available";
		}
		System.out.println("Genre: " + genre);
		
		if (mi.getDistributor() != null) {
			distributor = mi.getDistributor();
		} else {
			distributor = "Distributor Not Available";
		}
		System.out.println("Distributor: " + distributor);
		
		if (mi.getDomesticGross() != null) {
			domesticGross = mi.getDomesticGross();
		} else {
			domesticGross = "Domestic Gross Not Available";
		}
		System.out.println("Domestic Gross: " + domesticGross);
		
		if (mi.getWorldwideGross() != null) {
			worldwideGross = mi.getWorldwideGross();
		} else {
			worldwideGross = "Worldwide Gross Not Available";
		}
		System.out.println("Worldwide Gross: " + worldwideGross);
		
		if (mi.getBudget() != null) {
			budget = mi.getBudget();
		} else {
			budget = "Budget Not Available";
		}
		System.out.println("Budget: " + budget);
		
		if (mi.getPlot() != null) {
			plot = mi.getPlot();
		} else {
			plot = "Plot Not Available";
		}
		System.out.println("Plot: " + plot);
	
		String actorStr = "";
		String producerStr = "";
		String directorStr = "";
		
		if (mi.getActors() != null) {
			for (String actor : mi.getActors()) {
				/*if (!actorStr.equals("")) {
					actorStr += "\n";
				}*/
				actorStr += "\n- " + actor;
			}
		} else {
			actorStr = "Actor not available";
		}
		System.out.println("Actor: " + actorStr);
		
		if (mi.getProducers() != null) {
			for (String producer : mi.getProducers()) {
				/*if (!producerStr.equals("")) {
					producerStr += "\n";
				}*/
				producerStr += "\n- " + producer;
			}
		} else {
			producerStr = "Producer not available";
		}
		System.out.println("Producer: " + producerStr);
		
		if (mi.getDirectors() != null) {
			for (String director : mi.getDirectors()) {
				/*if (!directorStr.equals("")) {
					directorStr += "\n";
				}*/
				directorStr += "\n- " + director;
			}
		} else {
			directorStr = "Director not avalable";
		}
		System.out.println("Director: " + directorStr + "\n");
	}
	
	public static void main(String[] args) throws Exception {
		MainProgram mp = new MainProgram();
		while (true) {
			System.out.println("\n"
					+ "-------------------------------------------------\n"
					+ "|Type M to search by name of movie              |\n"
					+ "|Type A to search by name of actor              |\n"
					+ "|Type Exit to quit the program                  |\n"
					+ "-------------------------------------------------\n"
					+ "Please type: ");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			if (input.equals("M") || input.equals("m")) {
				System.out.println("Search by movie's name");
				System.out.println("You're looking for? :");
				String movieName = scanner.nextLine();
				movieName = movieName.toLowerCase();
				movieName = WordUtils.capitalize(movieName);
				MovieInfo mi = mp.searchByName(movieName, "en");
				if (mi == null) {
					System.out.println("We do not have any information about the movie "+ 
				movieName + "\n");
				} else {
					mp.showInfoMovie(mi);
				}
			} else if (input.equals("A") || input.equals("a")) {
				System.out.println("Search by actor's name");
				System.out.println("You're looking for? :");
				String actorName = scanner.nextLine();
				actorName = actorName.toLowerCase();
				actorName = WordUtils.capitalize(actorName, ' ');
				mp.searchByActor(actorName, "en");
			} else if (input.equals("Exit") || input.equals("exit")) {
				System.out.println("Quiting\n");
				System.exit(0); 
				break;
			} else {
				System.out.println("Option not available. Please try again");
			}
		}
		
	}
}

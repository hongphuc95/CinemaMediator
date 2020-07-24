package ied;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupParser { 
	
	public void JsoupParser() {
		
	}
	
private Document getDoc(String url) throws IOException {
	return Jsoup.connect(url).get();
}
public static void main(String[] args) throws IOException {
	JSoupParser jp = new JSoupParser();
	ArrayList<MovieInfo> movies;
	String[] genres = {"Adventure", "Comedy", "Drama", "Action", "Thriller-or-Suspense", "Romantic-Comedy"};
	String[] years = {"2000", "2001", "2002", "2003", "2004", "2005", 
			"2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015"};
	MovieInfo movie;
	
		for (String genre : genres) {
			String csvFile = "/Users/phucvu/Desktop/Universite/Master/IED/Projet/outputJava/"
					+ genre + ".csv";
			List<String> header = Arrays.asList(
					"Genre",
					"Year", 
					"Rank", 
					"Movie",
					"Release Date",
					"Therical Distributor",
					"MPAA Rating",
					"Gross",
					"Ticket Sold"
					);
			FileWriter writer = new FileWriter(csvFile);
			CSVUtils.writeLine(writer, header); // Write header
			for (String year : years) {
				String url = "https://www.the-numbers.com/market/" + year + "/genre/" + genre;
				Document doc = jp.getDoc(url);
				Elements table = doc.select("div#page_filling_chart table tbody tr");
				/*List<String> header = Arrays.asList(
						"Genre",
						"Year", 
						table.select("th:eq(0)").text(), 
						table.select("th:eq(1)").text(),
						table.select("th:eq(2)").text(),
						table.select("th:eq(3)").text(),
						table.select("th:eq(4)").text(),
						"Gross",
						table.select("th:eq(6)").text()
						);*/
				
				Elements tableElements = doc.select("tr:gt(0)" + ":lt" + "(" + (table.size()-2) + ")" );
				movies = new ArrayList<>();
				
				for (Element element : tableElements) {
					movie = new MovieInfo();
					movie.setRank(element.select("td:eq(0)").text());
					movie.setMovie(element.select("td:eq(1)").text());
					movie.setReleaseDate((element.select("td:eq(2)").text()));
					movie.setDistributor(element.select("td:eq(3)").text());
					movie.setRating(element.select("td:eq(4)").text());
					movie.setDomesticGross(element.select("td:eq(5)").text());
					movie.setTicketsSold(element.select("td:eq(6)").text());
					movies.add(movie);
				}
				
				for (MovieInfo mi : movies) {
					List<String> list = new ArrayList<>();
					list.add(genre);
					list.add(year);
					list.add(mi.getRank());
					list.add(mi.getMovie());
					list.add(mi.getReleaseDate());
					list.add(mi.getDistributor());
					list.add(mi.getRating());
					list.add(mi.getDomesticGross());
					list.add(mi.getTicketsSold());
			
					CSVUtils.writeLine(writer, list);
				}
			}
			writer.flush();
			writer.close();
		}
}
}

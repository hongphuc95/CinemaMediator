package ied;

import java.util.ArrayList;
import java.util.Date;

public class MovieInfo {
private String rank;
private String movie;
private String releaseDate;
private String genre;
private String distributor;
private String budget;
private String domesticGross;
private String worldwideGross;
private String ticketsSold;
private String plot;
private String rating;
private ArrayList<String> directors;
private ArrayList<String> producers;
private ArrayList<String> actors;

public void MovieInfo() {
	
}

public String getRank() {
	return rank;
}

public void setRank(String rank) {
	this.rank = rank;
}

public String getMovie() {
	return movie;
}

public void setMovie(String movie) {
	this.movie = movie;
}

public String getReleaseDate() {
	return releaseDate;
}

public void setReleaseDate(String releaseDate) {
	this.releaseDate = releaseDate;
}

public String getGenre() {
	return genre;
}

public void setGenre(String genre) {
	this.genre = genre;
}

public String getDistributor() {
	return distributor;
}

public void setDistributor(String distributor) {
	this.distributor = distributor;
}

public String getBudget() {
	return budget;
}

public void setBudget(String budget) {
	this.budget = budget;
}

public String getDomesticGross() {
	return domesticGross;
}

public void setDomesticGross(String domesticGross) {
	this.domesticGross = domesticGross;
}

public String getWorldwideGross() {
	return worldwideGross;
}

public void setWorldwideGross(String worldwideGross) {
	this.worldwideGross = worldwideGross;
}

public String getTicketsSold() {
	return ticketsSold;
}

public void setTicketsSold(String ticketsSold) {
	this.ticketsSold = ticketsSold;
}

public String getPlot() {
	return plot;
}

public void setPlot(String plot) {
	this.plot = plot;
}

public ArrayList<String> getDirectors() {
	return directors;
}

public void setDirectors(ArrayList<String> directors) {
	this.directors = directors;
}

public ArrayList<String> getProducers() {
	return producers;
}

public void setProducers(ArrayList<String> producers) {
	this.producers = producers;
}

public ArrayList<String> getActors() {
	return actors;
}

public void setActors(ArrayList<String> actors) {
	this.actors = actors;
}

public String getRating() {
	return rating;
}

public void setRating(String rating) {
	this.rating = rating;
}


}
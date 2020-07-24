package ied;

import java.util.ArrayList;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

public class LinkedMovieJob {
	ArrayList<String> producers = new ArrayList<>();
	ArrayList<String> directors = new ArrayList<>();
	ArrayList<String> actors = new ArrayList<>();
	String prefixes = 
			"" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX dbpedia2: <http://dbpedia.org/property/>\n" +
            "PREFIX dbpedia: <http://dbpedia.org/>\n" +
            "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
            "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n";
	
	public ParameterizedSparqlString directorQuery(String movieName, String lang) {
		
		 ParameterizedSparqlString qs = new ParameterizedSparqlString
				 ( "" +
	                 prefixes +
	                "\n" +
	                "SELECT DISTINCT ?director\n" +
	                "WHERE {\n" +
	                "?film a <http://dbpedia.org/ontology/Film> ;\n" +
	                "foaf:name ?movieLiteral ;\n" +
	                "dbo:director ?directorObject .\n" +
	                "?directorObject foaf:name ?director .\n" +
	                "}"
	                );

	        Literal movieLiteral = ResourceFactory.createLangLiteral( movieName, lang );
	        qs.setParam( "movieLiteral", movieLiteral );
		return qs;
	}
	
	public ParameterizedSparqlString producerQuery(String movieName, String lang) {
		
		 ParameterizedSparqlString qs = new ParameterizedSparqlString
				 ( "" +
	                 prefixes +
	                "\n" +
	                "SELECT DISTINCT ?producer\n" +
	                "WHERE {\n" +
	                "?film a <http://dbpedia.org/ontology/Film> ;\n" +
	                "foaf:name ?movieLiteral ;\n" +
	                "dbo:producer ?producerObject .\n" +
	                "?producerObject foaf:name ?producer .\n" +
	                "}"
	                );

	        Literal movieLiteral = ResourceFactory.createLangLiteral( movieName, lang );
	        qs.setParam( "movieLiteral", movieLiteral );
		return qs;
	}
	
	public ParameterizedSparqlString actorQuery(String movieName, String lang) {
		
		 ParameterizedSparqlString qs = new ParameterizedSparqlString
				 ( "" +
	                 prefixes +
	                "\n" +
	                "SELECT DISTINCT ?actor\n" +
	                "WHERE {\n" +
	                "?film a <http://dbpedia.org/ontology/Film> ;\n" +
	                "foaf:name ?movieLiteral ;\n" +
	                "dbo:starring ?actorObject .\n" +
	                "?actorObject foaf:name ?actor .\n" +
	                "}"
	                );

	        Literal movieLiteral = ResourceFactory.createLangLiteral( movieName, lang );
	        qs.setParam( "movieLiteral", movieLiteral );
	        //System.out.println(qs);
		return qs;
	}
	
	public ParameterizedSparqlString movieQuery(String actor, String lang) {
		
		 ParameterizedSparqlString qs = new ParameterizedSparqlString
				 ( "" +
	                 prefixes +
	                "\n" +
	                "SELECT DISTINCT ?movie\n" +
	                "WHERE {\n" +
	                "?film a <http://dbpedia.org/ontology/Film> ;\n" +
	                "dbo:starring ?actorObject ;\n" +
	                "foaf:name ?movie .\n" +
	                "?actorObject foaf:name ?actor .\n" +
	                "}"
	                );

	        Literal actorLiteral = ResourceFactory.createLangLiteral( actor, lang );
	        qs.setParam( "actor", actorLiteral );
	        //System.out.println(qs);
		return qs;
	}
	
	public ResultSet queryResults(ParameterizedSparqlString qs) {
		QueryExecution exec = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", qs.asQuery() );

        // Normally you'd just do results = exec.execSelect(), but I want to 
        // use this ResultSet twice, so I'm making a copy of it.  
        ResultSet results = ResultSetFactory.copyResults( exec.execSelect() );
        return results;
	}
	
	public void producerResult(MovieInfo mi, ResultSet results) {
		while ( results.hasNext() ) {
			QuerySolution result = results.next();
			producers.add(result.get( "producer").toString().replaceAll("@.*", ""));
			mi.setProducers(producers);
		}
	}
	
	public void directorResult(MovieInfo mi, ResultSet results) {
		while ( results.hasNext() ) {
			QuerySolution result = results.next();
			directors.add(result.get( "director").toString().replaceAll("@.*", ""));
			mi.setDirectors(directors);
		}
	}
	
	public void actorResult(MovieInfo mi, ResultSet results) {
		while ( results.hasNext() ) {
			QuerySolution result = results.next();
			actors.add(result.get( "actor").toString().replaceAll("@.*", ""));
			mi.setActors(actors);
		}
	}
	
	public ArrayList<String> movieResult(ResultSet results) {
		ArrayList<String> movies = new ArrayList<>();
		while ( results.hasNext() ) {
			QuerySolution result = results.next();
            movies.add(result.get("movie").toString());
        }
		return movies;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void personResult(MovieInfo mi, ResultSet results) {
		while ( results.hasNext() ) {
			QuerySolution result = results.next();
            producers.add(result.get( "producer").toString().replaceAll("@.*", ""));
            directors.add(result.get( "director").toString().replaceAll("@.*", ""));
            actors.add(result.get( "actor").toString().replaceAll("@.*", ""));
        }
		
		mi.setDirectors(directors);
		mi.setProducers(producers);
		mi.setActors(actors);
		
		String actorStr = "Actors: ";
		String directorStr = "Directors: ";
		String producerStr = "Producers: ";
		for (String actor : actors) {
			actorStr += actor + ", ";
		}
		for (String producer : producers) {
			producerStr += producer + ", ";
		}
		for (String director : directors) {
			directorStr += director + ", ";
		}
		System.out.println(actorStr);
		System.out.println(producerStr);
		System.out.println(directorStr);
	}
	
	public ParameterizedSparqlString personQuery(String movieName, String lang) {
		
		 ParameterizedSparqlString qs = new ParameterizedSparqlString
				 ( "" +
	                 prefixes +
	                "\n" +
	                "SELECT DISTINCT ?director ?producer ?actor\n" +
	                "WHERE {\n" +
	                "?film a <http://dbpedia.org/ontology/Film> ;\n" +
	                "foaf:name ?movieLiteral ;\n" +
	                "dbo:director ?directorObject ;\n" +
	                "dbo:producer ?producerObject ;\n" +
	                "dbo:starring ?actorObject .\n" +
	                "?producerObject foaf:name ?producer .\n" +
	                "?directorObject foaf:name ?director .\n" +
	                "?actorObject foaf:name ?actor .\n" +
	                "}"
	                );

	        Literal movieLiteral = ResourceFactory.createLangLiteral( movieName, lang );
	        qs.setParam( "movieLiteral", movieLiteral );
	        System.out.println(qs);
		return qs;
	}
}

package ied;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class JDBCJob {
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public void JDBC() {
    	
    }
	
	protected MovieInfo readDataBase(String movie) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/PROJECT?"
                        + "user=hvu&password=bananhphuc");

        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        String request = "SELECT * FROM Film";
        if (movie != null && !movie.equals("")) {
        	request += " WHERE Movie LIKE " + "\'" + movie + "\'";
        }
        resultSet = statement
                .executeQuery(request);
        
        MovieInfo mi = new MovieInfo();
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            if (resultSet.getString("Movie") != null) {
            	mi.setMovie(resultSet.getString("Movie"));
            }
            //System.out.println("Movie Debug InLoop: " + mi.getMovie());
            if (resultSet.getString("Genre") != null) {
            	mi.setGenre(resultSet.getString("Genre"));
            }
            if (resultSet.getString("Distributor") != null) {
            	mi.setDistributor(resultSet.getString("Distributor"));
            }
            if (resultSet.getString("ProductionBudget") != null) {
            	mi.setBudget(resultSet.getString("ProductionBudget"));
            }
            if (resultSet.getString("DomesticGross") != null) {
            	mi.setDomesticGross(resultSet.getString("DomesticGross"));
            }
            if (resultSet.getString("WorldwideGross") != null) {
            	mi.setWorldwideGross(resultSet.getString("WorldwideGross"));
            }
            if (resultSet.getString("ReleaseDate") != null) {
            	mi.setReleaseDate(resultSet.getString("ReleaseDate"));
            }
        }
        //System.out.println("Movie Debug OutLoop: " + mi.getMovie());
        if (mi.getMovie() != null && !mi.getMovie().equals("")) {
        	return mi;
        } else {
        	return null;
        }
        
	}

}

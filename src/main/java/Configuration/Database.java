package Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    /* Start Singleton */
    private static Database instance = null;

    public static Database getInstance()
    {
        if(instance == null) {
            instance = new Database();
        }

        return instance;
    }
    /* End Singleton */

    private Connection connection;

    protected Database()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.makeConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void makeConnection() {
        try {
            this.connection = DriverManager.getConnection(Properties.getInstance().getProperty("databaseDriver"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute SQL query that results in a ResultSet of data
     *
     * @param query The SQL query
     * @param parameters Parameters that need to be injected in the query
     *
     * @return Result of the query when executed
     */
    public ResultSet ExecuteQuery(String query, ArrayList<String> parameters)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for(int i = 0;i<parameters.size();i++) {
                statement.setString(i+1, parameters.get(i));
            }
            return statement.executeQuery();
        } catch (Exception e) {
            this.makeConnection();
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Execute the SQL query that updates rows in the database and does not result in a ResultSet of data
     * @param query The SQL query
     * @param parameters Parameters that need to be injected in the query
     */
    public void ExecuteUpdate(String query, ArrayList<String> parameters)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for(int i = 0;i<parameters.size();i++) {
                statement.setString(i+1, parameters.get(i));
            }
            statement.executeUpdate();
        } catch (Exception e) {
            this.makeConnection();
            e.printStackTrace();
        }
    }

    /**
     * Init the database for the server. Executes queries in the databases.sql,
     * mainly for creating tables when they do not exist yet
     */
    public void init() {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/databases.sql")));
            String line;
            while ((line = br.readLine()) != null) {
                Database.getInstance().ExecuteUpdate(line, new ArrayList<String>());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package electricity.system;

import java.sql.*;

public class Database {
    public Connection connection;
    Statement statement;
    public Database(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/databaseName", "yourMySQLusername", "yourMySQLpassword");
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

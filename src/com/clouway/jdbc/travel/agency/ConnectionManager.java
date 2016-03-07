package com.clouway.jdbc.travel.agency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class ConnectionManager {

    public Connection getConnection(String databaseName, String username, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost/"+databaseName, username, password);
    }
}

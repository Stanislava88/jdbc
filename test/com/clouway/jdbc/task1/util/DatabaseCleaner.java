package com.clouway.jdbc.task1.util;

import com.clouway.practice.ConnectionConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseCleaner {

    private final String[] tables;

    public DatabaseCleaner(String... tables) {
        this.tables = tables;
    }

    public void cleanUp() {
        Connection connection = ConnectionConfiguration.getConnection();
        for (String table : tables) {
            try {
                connection.createStatement().execute("delete from " + table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

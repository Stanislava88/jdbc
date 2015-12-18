package com.clouway.jdbc.task1.util;

import com.clouway.practice.ConnectionConfiguration;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseCleaner {

    private final DataSource dataSource;
    private final String[] tables;

    public DatabaseCleaner(DataSource dataSource, String... users) {
        this.dataSource = dataSource;
        this.tables = users;
    }

    public void cleanUp() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

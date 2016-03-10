package com.clouway.jdbc.travel.agency;

import java.sql.*;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class DatabaseTableTool {
    private Connection connection;

    public DatabaseTableTool(Connection connection) {
        this.connection = connection;
    }

    public void clearClientRepository() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE people CASCADE ;");
        statement.close();
    }

    public void clearTripRepository() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE trip CASCADE ;");
        statement.close();
    }

    public boolean tableExist(String tableName) throws SQLException {
        String tableExistsQuery = "SELECT exists ( SELECT 1 FROM information_schema.tables WHERE table_name = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(tableExistsQuery);
        preparedStatement.setString(1, tableName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean tableExist = resultSet.getBoolean(1);
        resultSet.close();
        preparedStatement.close();
        return tableExist;
    }

    public void destroyTable(String tableName) throws SQLException {
        String dropTable = String.format("DROP TABLE IF EXISTS %s CASCADE;", tableName);
        Statement statement = connection.createStatement();
        statement.execute(dropTable);
        statement.close();
    }

    public void createRepository(String tableStructure) throws SQLException {
        String createUserTable = "CREATE TABLE " + tableStructure + ";";
        Statement statement = connection.createStatement();
        statement.execute(createUserTable);
        statement.close();
    }
}

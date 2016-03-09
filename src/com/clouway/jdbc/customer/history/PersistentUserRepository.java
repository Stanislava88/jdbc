package com.clouway.jdbc.customer.history;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepository {
    private Connection connection;
    private boolean trackingHistory;

    public PersistentUserRepository(Connection connection) {
        this.connection = connection;
    }

    public void clear() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE customer CASCADE;");
        statement.close();
    }

    public void registerCustomer(Customer customer) {
        String sqlStatement = "INSERT INTO customer VALUES(?, ?, ?, ?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customer.id);
            preparedStatement.setString(2, customer.name);
            preparedStatement.setString(3, customer.lastName);
            preparedStatement.setString(4, customer.egn);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomer(int id) throws SQLException {
        String selectById = "SELECT * FROM customer WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int customerId = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("last_name");
            String egn = resultSet.getString("egn");
            preparedStatement.close();
            resultSet.close();
            return new Customer(customerId, name, lastName, egn);
        } else {
            throw new NoSuchElementException("No users with such id.");
        }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sqlStatement = "UPDATE customer SET name=?, last_name=?, egn=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, customer.name);
        preparedStatement.setString(2, customer.lastName);
        preparedStatement.setString(3, customer.egn);
        preparedStatement.setInt(4, customer.id);


        preparedStatement.execute();
        preparedStatement.close();
    }

    public void trackUpdateHistory(boolean tracking) throws SQLException {
        Statement statement = connection.createStatement();
        if (tracking && !trackingHistory) {
            this.trackingHistory = true;
            statement.execute("CREATE OR REPLACE FUNCTION customer_history_procedure() RETURNS TRIGGER AS $customer_history$ " +
                    "BEGIN " +
                    "INSERT INTO customer_history(change_date, customer_id, name, last_name, egn) " +
                    "VALUES(CURRENT_TIMESTAMP, OLD.id, OLD.name, OLD.last_name, OLD.egn); " +
                    "RETURN NEW; " +
                    "END " +
                    "$customer_history$ LANGUAGE plpgsql; ");
            statement.execute("CREATE TRIGGER customer_history BEFORE UPDATE ON customer " +
                    "FOR EACH ROW EXECUTE PROCEDURE customer_history_procedure();");

        } else if (!tracking) {
            statement.execute("DROP TRIGGER customer_history ON customer;");
            statement.execute("DROP FUNCTION customer_history_procedure();");
            this.trackingHistory = false;
        }

    }

    public CustomerRecord getLastCustomerUpdate(int id) throws SQLException {
        String selectById = "SELECT * FROM customer_history WHERE customer_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int recordId = resultSet.getInt("id");
        Timestamp changeDate = resultSet.getTimestamp("change_date");
        int customerId = resultSet.getInt("customer_id");
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("last_name");
        String egn = resultSet.getString("egn");
        return new CustomerRecord(recordId, changeDate, customerId, name, lastName, egn);
    }

    public List<CustomerRecord> getCustomerHistory(int id) throws SQLException {
        String selectById = "SELECT * FROM customer_history WHERE customer_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getRecords(resultSet);
    }

    public List<CustomerRecord> getUpdateHistory() throws SQLException {
        String selectById = "SELECT * FROM customer_history";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectById);
        return getRecords(resultSet);
    }

    private List<CustomerRecord> getRecords(ResultSet resultSet) throws SQLException {
        List<CustomerRecord> customerRecords = new ArrayList<>();
        while (resultSet.next()) {
            int recordId = resultSet.getInt("id");
            Timestamp changeDate = resultSet.getTimestamp("change_date");
            int customerId = resultSet.getInt("customer_id");
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("last_name");
            String egn = resultSet.getString("egn");
            customerRecords.add(new CustomerRecord(recordId, changeDate, customerId, name, lastName, egn));
        }
        return customerRecords;
    }
}

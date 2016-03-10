package com.clouway.jdbc.customer.history.persistence;

import com.clouway.jdbc.customer.history.CustomerRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentCustomerHistoryRepository {
    private Connection connection;
    private boolean trackingHistory;

    public PersistentCustomerHistoryRepository(Connection connection) {
        this.connection = connection;
    }

    public void trackUpdates(boolean tracking) throws SQLException {
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

    public CustomerRecord getLastUpdate(int id) throws SQLException {
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

    public List<CustomerRecord> getById(int id) throws SQLException {
        String selectById = "SELECT * FROM customer_history WHERE customer_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getRecords(resultSet);
    }

    public List<CustomerRecord> getUpdates() throws SQLException {
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

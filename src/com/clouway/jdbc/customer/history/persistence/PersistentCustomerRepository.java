package com.clouway.jdbc.customer.history.persistence;

import com.clouway.jdbc.customer.history.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentCustomerRepository {
    private Connection connection;

    public PersistentCustomerRepository(Connection connection) {
        this.connection = connection;
    }

    public void register(Customer customer) {
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

    public Customer getById(int id) throws SQLException {
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

    public void update(Customer customer) throws SQLException {
        String sqlStatement = "UPDATE customer SET name=?, last_name=?, egn=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, customer.name);
        preparedStatement.setString(2, customer.lastName);
        preparedStatement.setString(3, customer.egn);
        preparedStatement.setInt(4, customer.id);


        preparedStatement.execute();
        preparedStatement.close();
    }
}

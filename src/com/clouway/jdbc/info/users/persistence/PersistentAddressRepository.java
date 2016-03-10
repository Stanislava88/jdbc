package com.clouway.jdbc.info.users.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentAddressRepository {
    private Connection connection;

    public PersistentAddressRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(Address address) {
        String sqlStatement = "INSERT INTO address VALUES(?, ?, ?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, address.id);
            preparedStatement.setInt(2, address.userId);
            preparedStatement.setString(3, address.address);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Address getById(int id) throws SQLException {
        String selectById = "SELECT * FROM address WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int addressId = resultSet.getInt("id");
            int userId = resultSet.getInt("user_id");
            String address = resultSet.getString("address");
            return new Address(addressId, userId, address);
        } else {
            resultSet.close();
            preparedStatement.close();
            throw new NoSuchElementException("No users with such id.");
        }
    }

    public List<Address> getList() throws SQLException {
        String addressQuery = "SELECT * FROM address;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(addressQuery);
        List<Address> addressList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int userId = resultSet.getInt("user_id");
            String address = resultSet.getString("address");
            addressList.add(new Address(id, userId, address));
        }
        resultSet.close();
        statement.close();
        return addressList;
    }
}

package com.clouway.jdbc.info.users.persistence;

import com.clouway.jdbc.ExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentAddressRepository implements AddressRepository {
    private Connection connection;

    public PersistentAddressRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void register(Address address) {
        String sqlStatement = "INSERT INTO address VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, address.id);
            preparedStatement.setLong(2, address.userId);
            preparedStatement.setString(3, address.address);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new ExecutionException("Could not register the address");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Address findById(long id) {
        String selectById = "SELECT * FROM address WHERE id=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectById);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long addressId = resultSet.getLong("id");
                long userId = resultSet.getLong("user_id");
                String address = resultSet.getString("address");
                return new Address(addressId, userId, address);
            } else {
                throw new ExecutionException("No users with such id.");
            }
        } catch (SQLException e) {
            throw new ExecutionException("Could not find address with that id.");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Address> findAll() {
        String addressQuery = "SELECT * FROM address;";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(addressQuery);
            List<Address> addressList = new ArrayList<Address>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long userId = resultSet.getLong("user_id");
                String address = resultSet.getString("address");
                addressList.add(new Address(id, userId, address));
            }
            return addressList;
        } catch (SQLException e) {
            throw new ExecutionException("Could not get the list of addresses");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

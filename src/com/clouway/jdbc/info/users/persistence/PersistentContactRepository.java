package com.clouway.jdbc.info.users.persistence;

import com.clouway.jdbc.ExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentContactRepository implements ContactRepository {
    private Connection connection;

    public PersistentContactRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void register(Long contactId, Long userId, Long addressId) {
        String sqlStatement = "INSERT INTO contact VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, contactId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, addressId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new ExecutionException("Could not register the contact: " + userId);
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
    public Contact findById(Long id) {
        String selectById = "SELECT contact.id as id, users.name as name, " +
                "address.residence as residence, address.street as street FROM contact " +
                "INNER JOIN users on contact.user_id = users.id " +
                "INNER JOIN address ON contact.address_id = address.id WHERE contact.id=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectById);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long contactId = resultSet.getLong("id");
                String userName = resultSet.getString("name");
                String residence = resultSet.getString("residence");
                String street = resultSet.getString("street");
                return new Contact(contactId, userName, residence, street);
            } else {
                throw new ExecutionException("could not find contact with such id: " + id);
            }
        } catch (SQLException e) {
            throw new ExecutionException("could not find contact with such id: " + id);
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
    public List<Contact> findAll() {
        String contactQuery = "SELECT contact.id as id, users.name as name, " +
                "address.residence as residence, address.street as street FROM contact " +
                "INNER JOIN users on contact.user_id = users.id " +
                "INNER JOIN address ON contact.address_id = address.id;";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(contactQuery);
            List<Contact> contactList = new ArrayList<Contact>();
            while (resultSet.next()) {
                Long contactId = resultSet.getLong("id");
                String userName = resultSet.getString("name");
                String residence = resultSet.getString("residence");
                String street = resultSet.getString("street");
                contactList.add(new Contact(contactId, userName, residence, street));
            }
            return contactList;
        } catch (SQLException e) {
            throw new ExecutionException("Could not load the contact list");
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

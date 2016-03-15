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
    public void add(Contact contact) {
        String sqlStatement = "INSERT INTO contact VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, contact.id);
            preparedStatement.setInt(2, contact.userId);
            preparedStatement.setString(3, contact.number);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new ExecutionException("Could not add the contact: " + contact.id);
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
    public Contact getById(int id) {
        String selectById = "SELECT * FROM contact WHERE id=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectById);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int contactId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String number = resultSet.getString("phone_number");
                return new Contact(contactId, userId, number);
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
        String contactQuery = "SELECT * FROM contact;";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(contactQuery);
            List<Contact> contactList = new ArrayList<Contact>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String number = resultSet.getString("phone_number");
                contactList.add(new Contact(id, userId, number));
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

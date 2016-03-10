package com.clouway.jdbc.info.users.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentContactRepository {
    private Connection connection;

    public PersistentContactRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(Contact contact) {
        String sqlStatement = "INSERT INTO contact VALUES(?, ?, ?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, contact.id);
            preparedStatement.setInt(2, contact.userId);
            preparedStatement.setString(3, contact.number);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getById(int id) throws SQLException {
        String selectById = "SELECT * FROM contact WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int contactId = resultSet.getInt("id");
            int userId = resultSet.getInt("user_id");
            String number = resultSet.getString("phone_number");
            return new Contact(contactId, userId, number);
        } else {
            resultSet.close();
            preparedStatement.close();
            throw new NoSuchElementException("No users with such id.");
        }
    }

    public List<Contact> getList() throws SQLException {
        String contactQuery = "SELECT * FROM contact;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(contactQuery);
        List<Contact> contactList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int userId = resultSet.getInt("user_id");
            String number = resultSet.getString("phone_number");
            contactList.add(new Contact(id, userId, number));
        }
        resultSet.close();
        statement.close();
        return contactList;
    }
}

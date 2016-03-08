package com.clouway.jdbc.info.users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepository {
    private Connection connection;

    public PersistentUserRepository(Connection connection) {
        this.connection = connection;
    }

    public void clear() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("TRUNCATE TABLE users CASCADE;");
        statement.execute("TRUNCATE TABLE contact;");
        statement.execute("TRUNCATE TABLE address;");
        statement.close();
    }

    public void registerUser(User user) {
        String sqlStatement = "INSERT INTO users VALUES(?, ?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, user.id);
            preparedStatement.setString(2, user.name);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int id) throws SQLException {
        String selectById = "SELECT * FROM users WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String name = resultSet.getString("name");

            preparedStatement.close();
            resultSet.close();
            return new User(userId, name);
        } else {
            resultSet.close();
            preparedStatement.close();
            throw new NoSuchElementException("No users with such id.");
        }
    }

    public List<User> getUsersList() throws SQLException {
        String usersQuery = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(usersQuery);
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            userList.add(new User(id, name));
        }
        resultSet.close();
        statement.close();
        return userList;
    }

    public void addContact(Contact contact) {
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

    public Contact getContact(int id) throws SQLException {
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

    public List<Contact> getContactList() throws SQLException {
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

    public void addAddress(Address address) {
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

    public Address getAddress(int id) throws SQLException {
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

    public List<Address> getAddressList() throws SQLException {
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

package com.clouway.crm.adapter.jdbc;

import com.clouway.crm.core.Address;
import com.clouway.crm.core.Contact;
import com.clouway.crm.core.User;
import com.clouway.crm.core.UserContactRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserContactRepository implements UserContactRepository {
  private Connection connection;

  public PersistentUserContactRepository(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void registerUser(User user) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(userId,name,age)VALUES (?,?,?)")) {
      preparedStatement.setInt(1, user.id);
      preparedStatement.setString(2, user.name);
      preparedStatement.setInt(3, user.age);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public User findById(int id) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE userID=?")) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        return new User(id, name, age);
      }
    }
    return null;
  }

  @Override
  public List<User> findAllUsers() throws SQLException {
    List<User> users = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")) {

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt("userId");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        users.add(new User(id, name, age));
      }
    }
    return users;
  }

  @Override
  public void registerAddress(Address address) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO address(addressId,city,street,number)VALUES (?,?,?,?)")) {
      preparedStatement.setInt(1, address.id);
      preparedStatement.setString(2, address.city);
      preparedStatement.setString(3, address.street);
      preparedStatement.setInt(4, address.number);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public List<Address> findAllAddress() throws SQLException {
    List<Address> addresses = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM address")) {

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt("addressId");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");
        addresses.add(new Address(id, city, street, number));
      }
    }
    return addresses;
  }

  @Override
  public void registerContact(Contact contact) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact(userId,addressId) VALUES (?,?)")) {
      preparedStatement.setInt(1, contact.userId);
      preparedStatement.setInt(2, contact.addressId);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public List<Contact> findAllContacts() throws SQLException {
    List<Contact> contacts = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {


      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int userId = resultSet.getInt("userId");
        int addressId = resultSet.getInt("addressId");
        contacts.add(new Contact(userId, addressId));
      }
    }
    return contacts;
  }
}

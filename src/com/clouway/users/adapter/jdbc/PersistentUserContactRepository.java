package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Address;
import com.clouway.users.core.NewContact;
import com.clouway.users.core.Contact;
import com.clouway.users.core.Provider;
import com.clouway.users.core.User;
import com.clouway.users.core.UserContactRepository;

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
  private Provider<Connection> provider;

  public PersistentUserContactRepository(Provider provider) {
    this.provider = provider;
  }

  @Override
  public void register(NewContact newContact) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO contact(id,idUser,idAddress) VALUES (?,?,?)")) {
      preparedStatement.setInt(1, newContact.id);
      preparedStatement.setInt(2, newContact.userId);
      preparedStatement.setInt(3, newContact.userId);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }

  @Override
  public Contact findById(int id) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement(" select * from contact inner join users on contact.id=users.id" +
            " inner join address on contact.id=address.id WHERE contact.id=?")) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int userId = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        int age = resultSet.getInt("age");

        int addressId = resultSet.getInt("id");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");

        return new Contact(id, new User(userId, firstName, lastName, age), new Address(addressId, city, street, number));
      }
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public List<Contact> findAll() {
    List<Contact> contacts = new ArrayList<>();

    try (PreparedStatement preparedStatement = provider.get().prepareStatement(" select * from contact inner join users on contact.id=users.id" +
            " inner join address on contact.id=address.id")) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        int id = resultSet.getInt("id");

        int idUser = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        int age = resultSet.getInt("age");

        int idAddress = resultSet.getInt("id");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");

        contacts.add(new Contact(id, new User(idUser, firstName, lastName, age), new Address(idAddress, city, street, number)));
      }
      return contacts;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}

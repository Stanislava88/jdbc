package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Address;
import com.clouway.users.core.NewContact;
import com.clouway.users.core.Contact;
import com.clouway.users.core.Provider;
import com.clouway.users.core.User;
import com.clouway.users.core.UserContactRepository;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserNewContactRepositoryTest {
  private Provider<Connection> provider;
  private UserContactRepository repository;
  private PreparedStatement preparedStatement;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();
    repository = new PersistentUserContactRepository(provider);

    cleanUp();
  }

  @After
  public void tearDown() throws Exception {
    preparedStatement.close();
  }

  @Test
  public void happyPath() throws Exception {
    User user = new User(1, "Ivan", "Ivanov", 20);
    Address address = new Address(1, "Varna", "Pirotska", 20);

    NewContact newContact = new NewContact(1, user.id, address.id);
    Contact contact = new Contact(1, user, address);

    registerUsers(user);
    registerAddresses(address);

    repository.register(newContact);

    Contact actual = repository.findById(newContact.id);

    assertThat(actual, is(contact));
  }

  @Test
  public void findAll() throws Exception {
    User user1 = new User(1, "Ivan", "Ivanov", 20);
    User user2 = new User(2, "Maria", "Petrova", 18);
    registerUsers(user1, user2);

    Address address1 = new Address(1, "Varna", "Pirotska", 20);
    Address address2 = new Address(2, "Sofia", "Vitoshka", 30);
    registerAddresses(address1, address2);

    NewContact newContact1 = new NewContact(1, user1.id, address1.id);
    NewContact newContact2 = new NewContact(2, user2.id, address2.id);

    Contact contact1 = new Contact(1, user1, address1);
    Contact contact2 = new Contact(2, user2, address2);

    repository.register(newContact1);
    repository.register(newContact2);

    List<Contact> actual = repository.findAll();
    List<Contact> expected = Lists.newArrayList(contact1, contact2);

    assertThat(actual, is(expected));
  }

  private void setStatements(PreparedStatement preparedStatement, int id, String firstName, String lastName, int age) throws SQLException {
    preparedStatement.setInt(1, id);
    preparedStatement.setString(2, firstName);
    preparedStatement.setString(3, lastName);
    preparedStatement.setInt(4, age);

    preparedStatement.executeUpdate();
  }

  private void insertIntoUser(User user) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO users VALUES(?,?,?,?)")) {

      setStatements(preparedStatement, user.id, user.firstName, user.lastName, user.age);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void insertIntoAddress(Address address) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO address VALUES (?,?,?,?)")) {

      setStatements(preparedStatement, address.id, address.city, address.street, address.number);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void registerUsers(User... users) {
    for (User user : users) {
      insertIntoUser(user);
    }
  }

  private void registerAddresses(Address... addresses) {
    for (Address address : addresses) {
      insertIntoAddress(address);
    }
  }

  private void cleanUp() throws Exception {
    preparedStatement = provider.get().prepareStatement("TRUNCATE TABLE contact,users,address");
    preparedStatement.executeUpdate();
  }
}

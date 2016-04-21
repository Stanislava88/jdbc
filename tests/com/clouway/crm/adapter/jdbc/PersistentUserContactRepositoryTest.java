package com.clouway.crm.adapter.jdbc;

import com.clouway.crm.core.Address;
import com.clouway.crm.core.Contact;
import com.clouway.crm.core.User;
import com.clouway.crm.core.UserContactRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserContactRepositoryTest {
  private Connection connection;
  private UserContactRepository repository;
  private PreparedStatement preparedStatement;

  @Before
  public void setUp() throws Exception {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost/crm", "postgres", "clouway.com");
    repository = new PersistentUserContactRepository(connection);

    preparedStatement = connection.prepareStatement("TRUNCATE TABLE contact,users,address");
    preparedStatement.executeUpdate();
  }

  @Test
  public void happyPath() throws Exception {
    User user = new User(1, "Ivan", 20);
    repository.registerUser(user);

    User actual = repository.findById(1);

    assertThat(actual, is(user));
  }

  @Test
  public void findAllPeople() throws Exception {
    User user1 = new User(1, "Ivan", 26);
    User user2 = new User(2, "Maria", 26);

    repository.registerUser(user1);
    repository.registerUser(user2);

    List<User> actual = repository.findAllUsers();
    List<User> expected = Lists.newArrayList(user1, user2);

    assertThat(actual, is(expected));
  }

  @Test
  public void findAllAddress() throws Exception {
    Address address1 = new Address(1, "Veliko Tyrnovo", "Krakov", 21);
    Address address2 = new Address(2, "Veliko Tyrnovo", "Krakov", 18);

    repository.registerAddress(address1);
    repository.registerAddress(address2);

    List<Address> actual = repository.findAllAddress();
    List<Address> expected = Lists.newArrayList(address1, address2);

    assertThat(actual, is(expected));
  }

  @Test
  public void findAllContacts() throws Exception {
    User user1 = new User(1, "Ivan", 26);
    Address address1 = new Address(1, "Veliko Tyrnovo", "Krakov", 21);
    Contact contact = new Contact(1, 1);

    repository.registerUser(user1);
    repository.registerAddress(address1);

    repository.registerContact(contact);

    List<Contact> actual = repository.findAllContacts();
    List<Contact> expected = Lists.newArrayList(contact);

    assertThat(actual, is(expected));
  }
}

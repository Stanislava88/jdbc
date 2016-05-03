package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Address;
import com.clouway.users.core.AddressRepository;
import com.clouway.users.core.Provider;
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
public class PersistentAddressRepositoryTest {
  private Provider<Connection> provider;
  private PreparedStatement preparedStatement;
  private AddressRepository repository;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();
    repository = new PersistentAddressRepository(provider);

    cleanUp();
  }

  @After
  public void tearDown() throws Exception {
    preparedStatement.close();
  }

  @Test
  public void happyPath() throws Exception {
    Address address = new Address(1, "Veliko Tyrnovo", "Krakov", 21);

    repository.register(address);

    Address actual = repository.findById(1);

    assertThat(actual, is(address));
  }

  @Test
  public void findAll() throws Exception {
    Address address1 = new Address(1, "Varna", "Pirotska", 18);
    Address address2 = new Address(2, "Sofia", "Vitoshka", 26);

    repository.register(address1);
    repository.register(address2);

    List<Address> actual = repository.findAll();
    List<Address> expected = Lists.newArrayList(address1, address2);

    assertThat(actual, is(expected));
  }

  private void cleanUp() {
    try {
      preparedStatement = provider.get().prepareStatement("TRUNCATE users,address,contact");
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

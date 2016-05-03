package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Provider;
import com.clouway.users.core.User;
import com.clouway.users.core.UserRepository;
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
public class PersistentUserRepositoryTest {
  private Provider<Connection> provider;
  private PreparedStatement preparedStatement;
  private UserRepository repository;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();
    repository = new PersistentUserRepository(provider);

    cleanUp();
  }

  @After
  public void tearDown() throws Exception {
    preparedStatement.close();
  }

  @Test
  public void happyPath() throws Exception {
    User user = new User(1, "Ivan", "Ivanov", 20);
    repository.register(user);

    User actual = repository.findById(1);

    assertThat(actual, is(user));
  }

  @Test
  public void findAll() throws Exception {
    User user1 = new User(1, "Maria", "Petrova", 20);
    User user2 = new User(2, "Ivan", "Petkov", 22);

    repository.register(user1);
    repository.register(user2);

    List<User> actual = repository.findAll();
    List<User> expected = Lists.newArrayList(user1, user2);

    assertThat(expected, is(actual));
  }

  private void cleanUp() {
    try {
      preparedStatement = provider.get().prepareStatement("TRUNCATE users CASCADE");

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

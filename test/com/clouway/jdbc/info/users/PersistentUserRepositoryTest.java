package com.clouway.jdbc.info.users;

import com.clouway.jdbc.info.users.persistence.PersistentUserRepository;
import com.clouway.jdbc.info.users.persistence.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepositoryTest {
    Connection connection = null;
    PersistentUserRepository userRepository = null;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("user_info", "postgres", "clouway.com");
        userRepository = new PersistentUserRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        clear();
        connection.close();
    }

    @Test
    public void addUser() throws SQLException {
        User sisi = new User(1, "Sisi");

        userRepository.register(sisi);

        User sisiReturned = userRepository.getById(sisi.id);
        assertThat(sisiReturned, is(equalTo(sisi)));
    }

    @Test
    public void getUsersList() throws SQLException {
        User marko = new User(1, "Marko");
        User petar = new User(2, "Petar");
        User petko = new User(3, "Petko");

        userRepository.register(marko);
        userRepository.register(petar);
        userRepository.register(petko);

        List<User> users = new ArrayList<>();
        users.add(marko);
        users.add(petar);
        users.add(petko);

        List<User> userList = userRepository.getList();
        assertThat(userList, is(equalTo(users)));
    }

    public void clear() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("TRUNCATE TABLE users CASCADE;");
        statement.close();
    }
}

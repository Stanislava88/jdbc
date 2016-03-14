package com.clouway.jdbc.info.users;

import com.clouway.jdbc.ConnectionManager;
import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.ExecutionException;
import com.clouway.jdbc.info.users.persistence.PersistentUserRepository;
import com.clouway.jdbc.info.users.persistence.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
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
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("user_info", "postgres", "clouway.com");
        userRepository = new PersistentUserRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        DatabaseTableTool tableTool = new DatabaseTableTool();
        tableTool.clearTable(connection, "users");
        connection.close();
    }

    @Test
    public void addUser() {
        User sisi = new User(1, "Sisi");

        userRepository.register(sisi);

        User sisiReturned = userRepository.getById(sisi.id);
        assertThat(sisiReturned, is(equalTo(sisi)));
    }

    @Test
    public void getUsersList() {
        User marko = new User(1, "Marko");
        User petar = new User(2, "Petar");
        User petko = new User(3, "Petko");

        userRepository.register(marko);
        userRepository.register(petar);
        userRepository.register(petko);

        List<User> users = new ArrayList<User>();
        users.add(marko);
        users.add(petar);
        users.add(petko);

        List<User> userList = userRepository.findAll();
        assertThat(userList, is(equalTo(users)));
    }

    @Test(expected = ExecutionException.class)
    public void addUserWithTakenId() {
        User pavel = new User(1, "Pavel");

        userRepository.register(pavel);
        User margaret = new User(1, "Margaret");
        userRepository.register(margaret);
    }

    @Test(expected = ExecutionException.class)
    public void getUserByUnregisteredId() {
        userRepository.getById(1);
    }

}

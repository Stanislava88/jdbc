package com.clouway.jdbc.info.users;

import com.clouway.jdbc.ConnectionManager;
import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.info.users.persistence.Contact;
import com.clouway.jdbc.info.users.persistence.PersistentContactRepository;
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
public class PersistentContactRepositoryTest {

    Connection connection = null;
    PersistentUserRepository userRepository = null;
    PersistentContactRepository contactRepository = null;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("user_info", "postgres", "clouway.com");
        userRepository = new PersistentUserRepository(connection);
        contactRepository = new PersistentContactRepository(connection);
        User kaloian = new User(1, "Kaloian");

        userRepository.register(kaloian);
    }

    @After
    public void tearDown() throws SQLException {
        DatabaseTableTool tableTool = new DatabaseTableTool();
        tableTool.clearTable(connection, "users");
        tableTool.clearTable(connection, "contact");
        connection.close();
    }

    @Test
    public void addContact() {
        Contact kaloianNumber = new Contact(1, 1, "08345");

        contactRepository.add(kaloianNumber);

        Contact kaloianNumberReturned = contactRepository.getById(1);
        assertThat(kaloianNumberReturned, is(equalTo(kaloianNumber)));
    }

    @Test
    public void getContactList() {
        Contact kaloianNumber = new Contact(1, 1, "08345");
        Contact kaloianSecondNumber = new Contact(2, 1, "0345");
        Contact kaloianThirdNumber = new Contact(3, 1, "099323");

        contactRepository.add(kaloianNumber);
        contactRepository.add(kaloianSecondNumber);
        contactRepository.add(kaloianThirdNumber);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(kaloianNumber);
        contacts.add(kaloianSecondNumber);
        contacts.add(kaloianThirdNumber);

        List<Contact> contactList = contactRepository.getList();
        assertThat(contactList, is(equalTo(contacts)));
    }

}

package com.clouway.jdbc.info.users;

import com.clouway.jdbc.ConnectionManager;
import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.ExecutionException;
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
        User user = new User(1, "Kaloian");

        userRepository.register(user);
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
        Contact userNumberExpected = new Contact(1, 1, "08345");

        contactRepository.add(userNumberExpected);

        Contact userNumberActual = contactRepository.findById(1);
        assertThat(userNumberActual, is(equalTo(userNumberExpected)));
    }

    @Test
    public void addAnotherContact() {
        Contact userNumberExpected = new Contact(1, 1, "08345");
        contactRepository.add(userNumberExpected);

        Contact userSecondExpectedNumber = new Contact(2, 1, "08345");
        contactRepository.add(userSecondExpectedNumber);

        Contact userNumberActual = contactRepository.findById(1);
        Contact userSecondActualNumber = contactRepository.findById(2);

        assertThat(userNumberActual, is(equalTo(userNumberExpected)));
        assertThat(userSecondActualNumber, is(equalTo(userSecondExpectedNumber)));
    }

    @Test
    public void getContactList() {
        pretendAddedContactsAre(new Contact(2, 1, "0345"), new Contact(3, 1, "099323"));

        List<Contact> actualContacts = contactRepository.findAll();
        List<Contact> expectedContacts = listContacts(new Contact(2, 1, "0345"), new Contact(3, 1, "099323"));
        assertThat(actualContacts, is(equalTo(expectedContacts)));

    }

    @Test(expected = ExecutionException.class)
    public void getConctactByUnregisteredId() {
        contactRepository.findById(1);
    }

    @Test(expected = ExecutionException.class)
    public void addContactWithTakenId() {
        Contact userNumber = new Contact(1, 1, "3456");
        contactRepository.add(userNumber);

        User secondUser = new User(1, "Veronika");
        userRepository.register(secondUser);
        Contact secondUserNumber = new Contact(1, 2, "89745");
        contactRepository.add(secondUserNumber);
    }

    private void pretendAddedContactsAre(Contact... contacts) {
        for (Contact contact : contacts) {
            contactRepository.add(contact);
        }
    }

    private List<Contact> listContacts(Contact... contacts) {
        List<Contact> contactList = new ArrayList<>();
        for (Contact contact : contacts) {
            contactList.add(contact);
        }
        return contactList;
    }
}

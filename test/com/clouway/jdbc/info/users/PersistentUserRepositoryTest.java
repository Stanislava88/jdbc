package com.clouway.jdbc.info.users;

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
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("user_info", "postgres", "clouway.com");
        userRepository = new PersistentUserRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        userRepository.clear();
        connection.close();
    }

    @Test
    public void addUser() throws SQLException {
        User sisi = new User(1, "Sisi");

        userRepository.registerUser(sisi);

        User sisiReturned = userRepository.getUser(sisi.id);
        assertThat(sisiReturned, is(equalTo(sisi)));
    }

    @Test
    public void getUsersList() throws SQLException {
        User marko = new User(1, "Marko");
        User petar = new User(2, "Petar");
        User petko = new User(3, "Petko");

        userRepository.registerUser(marko);
        userRepository.registerUser(petar);
        userRepository.registerUser(petko);

        List<User> users = new ArrayList<>();
        users.add(marko);
        users.add(petar);
        users.add(petko);

        List<User> userList = userRepository.getUsersList();
        assertThat(userList, is(equalTo(users)));
    }

    @Test
    public void addContact() throws SQLException {
        User kaloian = new User(1, "Kaloian");

        userRepository.registerUser(kaloian);

        Contact kaloianNumber = new Contact(1, 1, "08345");

        userRepository.addContact(kaloianNumber);

        Contact kaloianNumberReturned = userRepository.getContact(1);
        assertThat(kaloianNumberReturned, is(equalTo(kaloianNumber)));
    }

    @Test
    public void getContactList() throws SQLException {
        User kaloian = new User(1, "Kaloian");

        userRepository.registerUser(kaloian);

        Contact kaloianNumber = new Contact(1, 1, "08345");
        Contact kaloianSecondNumber = new Contact(2, 1, "0345");
        Contact kaloianThirdNumber = new Contact(3, 1, "099323");

        userRepository.addContact(kaloianNumber);
        userRepository.addContact(kaloianSecondNumber);
        userRepository.addContact(kaloianThirdNumber);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(kaloianNumber);
        contacts.add(kaloianSecondNumber);
        contacts.add(kaloianThirdNumber);

        List<Contact> contactList = userRepository.getContactList();
        assertThat(contactList, is(equalTo(contacts)));
    }

    @Test
    public void addAddress() throws SQLException {
        User ivan = new User(1, "Ivan");
        userRepository.registerUser(ivan);

        Address ivanAddress = new Address(1, 1, "verusha 2");
        userRepository.addAddress(ivanAddress);

        Address ivanAddressReturned = userRepository.getAddress(1);
        assertThat(ivanAddressReturned, is(equalTo(ivanAddress)));
    }

    @Test
    public void getAddressList() throws SQLException {
        User ivo = new User(1, "Ivo");
        userRepository.registerUser(ivo);

        Address ivoAddress = new Address(1, 1, "verusha 2");
        Address ivoSecondAddress = new Address(2, 1, "gabrovski 2");
        Address ivoThirdAddress = new Address(3, 1, "balgaria 1");
        userRepository.addAddress(ivoAddress);
        userRepository.addAddress(ivoSecondAddress);
        userRepository.addAddress(ivoThirdAddress);

        List<Address> addresses = new ArrayList<>();
        addresses.add(ivoAddress);
        addresses.add(ivoSecondAddress);
        addresses.add(ivoThirdAddress);

        List<Address> addressList = userRepository.getAddressList();
        assertThat(addressList, is(equalTo(addresses)));
    }
}

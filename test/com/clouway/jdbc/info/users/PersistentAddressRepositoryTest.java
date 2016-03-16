package com.clouway.jdbc.info.users;

import com.clouway.jdbc.ConnectionManager;
import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.ExecutionException;
import com.clouway.jdbc.info.users.persistence.Address;
import com.clouway.jdbc.info.users.persistence.PersistentAddressRepository;
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
public class PersistentAddressRepositoryTest {
    Connection connection;
    PersistentUserRepository userRepository;
    PersistentAddressRepository addressRepository;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("user_info", "postgres", "clouway.com");
        userRepository = new PersistentUserRepository(connection);
        addressRepository = new PersistentAddressRepository(connection);
        User ivan = new User(1, "Ivan");
        userRepository.register(ivan);
    }

    @After
    public void tearDown() throws SQLException {
        DatabaseTableTool tableTool = new DatabaseTableTool();
        tableTool.clearTable(connection, "users");
        tableTool.clearTable(connection, "address");
        connection.close();
    }

    @Test
    public void addAddress() {
        Address expectedAddress = new Address(1L, "turnovo", "verusha 2");
        addressRepository.register(expectedAddress);

        Address actualAddress = addressRepository.findById(1L);
        assertThat(actualAddress, is(expectedAddress));
    }

    @Test
    public void addAnotherAddress() {
        Address expectedAddress = new Address(1L, "sofia", "verusha 2");
        addressRepository.register(expectedAddress);

        Address expectedSecondAddress = new Address(2L, "Burgas", "bulgaria 1");
        addressRepository.register(expectedSecondAddress);

        Address actualAddress = addressRepository.findById(1L);
        Address actualSecondAddress = addressRepository.findById(2L);
        assertThat(actualAddress, is(equalTo(expectedAddress)));
        assertThat(actualSecondAddress, is(equalTo(expectedSecondAddress)));

    }

    @Test
    public void getAddressList() {
        pretendAddedAddressesAre(new Address(1L, "rom", "verusha 2"), new Address(2L, "Paris", "gabrovski 2"));

        List<Address> actualAddresses = addressRepository.findAll();
        List<Address> expectedAddresses = listAddresses(new Address(1L, "rom", "verusha 2"), new Address(2L, "Paris", "gabrovski 2"));
        assertThat(actualAddresses, is(equalTo(expectedAddresses)));
    }

    @Test(expected = ExecutionException.class)
    public void addAddressWithTakenId() {
        Address pavelAddress = new Address(1L, "London", "Pavel");

        addressRepository.register(pavelAddress);
        Address margaretAddress = new Address(1L, "Dimitrovgrad", "Margaret");
        addressRepository.register(margaretAddress);
    }

    @Test(expected = ExecutionException.class)
    public void getUnexistingAddress() {
        addressRepository.findById(1L);
    }

    private void pretendAddedAddressesAre(Address... addresses) {
        for (Address address : addresses) {
            addressRepository.register(address);
        }
    }


    private List<Address> listAddresses(Address... addresses) {
        List<Address> addressesList = new ArrayList<>();
        for (Address street : addresses) {
            addressesList.add(street);
        }
        return addressesList;
    }
}

package com.clouway.jdbc.info.users;

import com.clouway.jdbc.info.users.persistence.Address;
import com.clouway.jdbc.info.users.persistence.PersistentAddressRepository;
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
public class PersistentAddressRepositoryTest {
    Connection connection = null;
    PersistentUserRepository userRepository = null;
    PersistentAddressRepository addressRepository = null;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("user_info", "postgres", "clouway.com");
        userRepository = new PersistentUserRepository(connection);
        addressRepository = new PersistentAddressRepository(connection);
        User ivan = new User(1, "Ivan");
        userRepository.register(ivan);
    }

    @After
    public void tearDown() throws SQLException {
        clear();
        connection.close();
    }

    @Test
    public void addAddress() throws SQLException {
        Address ivanAddress = new Address(1, 1, "verusha 2");
        addressRepository.add(ivanAddress);

        Address ivanAddressReturned = addressRepository.getById(1);
        assertThat(ivanAddressReturned, is(equalTo(ivanAddress)));
    }

    @Test
    public void getAddressList() throws SQLException {
        Address ivanAddress = new Address(1, 1, "verusha 2");
        Address ivanSecondAddress = new Address(2, 1, "gabrovski 2");
        Address ivanThirdAddress = new Address(3, 1, "balgaria 1");
        addressRepository.add(ivanAddress);
        addressRepository.add(ivanSecondAddress);
        addressRepository.add(ivanThirdAddress);

        List<Address> addresses = new ArrayList<>();
        addresses.add(ivanAddress);
        addresses.add(ivanSecondAddress);
        addresses.add(ivanThirdAddress);

        List<Address> addressList = addressRepository.getList();
        assertThat(addressList, is(equalTo(addresses)));
    }

    public void clear() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("TRUNCATE TABLE users CASCADE;");
        statement.execute("TRUNCATE TABLE address;");
        statement.close();
    }
}

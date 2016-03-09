package com.clouway.jdbc.customer.history;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PermanentCustomerRepositoryTest {

    Connection connection = null;
    PersistentUserRepository customerRepository = null;


    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("customer", "postgres", "clouway.com");
        customerRepository = new PersistentUserRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        customerRepository.clear();
        connection.close();
    }

    @Test
    public void addCustomer() throws SQLException {
        Customer john = new Customer(1, "John", "Petkovic", "123");
        customerRepository.registerCustomer(john);

        assertThat(customerRepository.getCustomer(1), is(equalTo(john)));
    }

    @Test
    public void updateCustomer() throws SQLException {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.registerCustomer(jack);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.updateCustomer(jackUpdated);

        assertThat(customerRepository.getCustomer(1), is(equalTo(jackUpdated)));
    }

    @Test
    public void trackUpdateHistory() throws SQLException {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.registerCustomer(jack);

        customerRepository.trackUpdateHistory(true);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.updateCustomer(jackUpdated);

        customerRepository.trackUpdateHistory(false);
        CustomerRecord jackUpdateRecord = customerRepository.getLastCustomerUpdate(1);
        assertThat(jackUpdateRecord.name, is(equalTo(jack.name)));
        assertThat(jackUpdateRecord.lastName, is(equalTo(jack.lastName)));
        assertThat(jackUpdateRecord.egn, is(equalTo(jack.egn)));
        assertThat(customerRepository.getCustomer(1), is(equalTo(jackUpdated)));
    }

    @Test
    public void multipleUpdatesHistory() throws SQLException {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.registerCustomer(jack);

        customerRepository.trackUpdateHistory(true);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.updateCustomer(jackUpdated);

        Customer jackSecondUpdate = new Customer(1, "Jack", "Malkovic", "9434");
        customerRepository.updateCustomer(jackSecondUpdate);

        List<CustomerRecord> jackHistory = customerRepository.getCustomerHistory(1);
        customerRepository.trackUpdateHistory(false);
        assertThat(jackHistory.size(), is(equalTo(2)));
        assertThat(jackHistory.get(0).lastName, is(equalTo("Petkovic")));
        assertThat(jackHistory.get(1).lastName, is(equalTo("Paskalev")));
    }

    @Test
    public void allCustomersUpdateHistory() throws SQLException {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        Customer jill = new Customer(2, "Jill", "Malic", "56");
        customerRepository.registerCustomer(jack);
        customerRepository.registerCustomer(jill);

        customerRepository.trackUpdateHistory(true);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.updateCustomer(jackUpdated);

        Customer jillUpdated = new Customer(2, "Jill", "Petrovic", "9434");
        customerRepository.updateCustomer(jillUpdated);

        List<CustomerRecord> customerRecords = customerRepository.getUpdateHistory();

        customerRepository.trackUpdateHistory(false);
        assertThat(customerRecords.size(), is(equalTo(2)));
        assertThat(customerRecords.get(0).lastName, is(equalTo("Petkovic")));
        assertThat(customerRecords.get(1).lastName, is(equalTo("Malic")));
    }


}

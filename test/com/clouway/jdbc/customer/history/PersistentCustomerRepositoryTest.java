package com.clouway.jdbc.customer.history;

import com.clouway.jdbc.customer.history.persistence.PersistentCustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentCustomerRepositoryTest {

    Connection connection = null;
    PersistentCustomerRepository customerRepository = null;


    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("customer", "postgres", "clouway.com");
        customerRepository = new PersistentCustomerRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        clear();
        connection.close();
    }

    @Test
    public void addCustomer() throws SQLException {
        Customer john = new Customer(1, "John", "Petkovic", "123");
        customerRepository.register(john);

        assertThat(customerRepository.getById(1), is(equalTo(john)));
    }

    @Test
    public void updateCustomer() throws SQLException {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.register(jack);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.update(jackUpdated);

        assertThat(customerRepository.getById(1), is(equalTo(jackUpdated)));
    }

    public void clear() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE customer CASCADE;");
        statement.close();
    }
}

package com.clouway.jdbc.customer.history;

import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.customer.history.persistence.Customer;
import com.clouway.jdbc.customer.history.persistence.PersistentCustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

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
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("customer", "postgres", "clouway.com");
        customerRepository = new PersistentCustomerRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        DatabaseTableTool tableTool = new DatabaseTableTool();
        tableTool.clearTable(connection, "customer");
        connection.close();
    }

    @Test
    public void addCustomer() {
        Customer john = new Customer(1, "John", "Petkovic", "123");
        customerRepository.register(john);

        assertThat(customerRepository.getById(1), is(equalTo(john)));
    }

    @Test
    public void updateCustomer() {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.register(jack);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.update(jackUpdated);

        assertThat(customerRepository.getById(1), is(equalTo(jackUpdated)));
    }

}

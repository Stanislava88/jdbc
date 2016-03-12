package com.clouway.jdbc.customer.history;

import com.clouway.jdbc.ConnectionManager;
import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.customer.history.persistence.Customer;
import com.clouway.jdbc.customer.history.persistence.CustomerRecord;
import com.clouway.jdbc.customer.history.persistence.PersistentCustomerHistoryRepository;
import com.clouway.jdbc.customer.history.persistence.PersistentCustomerRepository;
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
public class PersistentCustomerHistoryRepositoryTest {
    Connection connection = null;
    PersistentCustomerRepository customerRepository = null;
    PersistentCustomerHistoryRepository customerHistory = null;


    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("customer", "postgres", "clouway.com");
        customerRepository = new PersistentCustomerRepository(connection);
        customerHistory = new PersistentCustomerHistoryRepository(connection);
    }

    @After
    public void tearDown() throws SQLException {
        DatabaseTableTool tableTool = new DatabaseTableTool();
        tableTool.clearTable(connection, "customer");
        tableTool.clearTable(connection, "customer_history");
        connection.close();
    }

    @Test
    public void trackUpdateHistory() {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.register(jack);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.update(jackUpdated);

        CustomerRecord jackUpdateRecord = customerHistory.getLastUpdate(1);
        assertThat(jackUpdateRecord.name, is(equalTo(jack.name)));
        assertThat(jackUpdateRecord.lastName, is(equalTo(jack.lastName)));
        assertThat(jackUpdateRecord.egn, is(equalTo(jack.egn)));
        assertThat(customerRepository.getById(1), is(equalTo(jackUpdated)));
    }

    @Test
    public void multipleUpdatesHistory() {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        customerRepository.register(jack);

        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.update(jackUpdated);

        Customer jackSecondUpdate = new Customer(1, "Jack", "Malkovic", "9434");
        customerRepository.update(jackSecondUpdate);

        List<CustomerRecord> jackHistory = customerHistory.getById(1);
        assertThat(jackHistory.size(), is(equalTo(2)));
        assertThat(jackHistory.get(0).lastName, is(equalTo("Petkovic")));
        assertThat(jackHistory.get(1).lastName, is(equalTo("Paskalev")));
    }

    @Test
    public void allCustomersUpdateHistory() {
        Customer jack = new Customer(1, "Jack", "Petkovic", "123");
        Customer jill = new Customer(2, "Jill", "Malic", "56");
        customerRepository.register(jack);
        customerRepository.register(jill);


        Customer jackUpdated = new Customer(1, "Jack", "Paskalev", "9434");
        customerRepository.update(jackUpdated);

        Customer jillUpdated = new Customer(2, "Jill", "Petrovic", "9434");
        customerRepository.update(jillUpdated);

        List<CustomerRecord> customerRecords = customerHistory.getUpdates();

        assertThat(customerRecords.size(), is(equalTo(2)));
        assertThat(customerRecords.get(0).lastName, is(equalTo("Petkovic")));
        assertThat(customerRecords.get(1).lastName, is(equalTo("Malic")));
    }

}

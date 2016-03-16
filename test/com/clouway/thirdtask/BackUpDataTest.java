package com.clouway.thirdtask;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by clouway on 16-3-8.
 */
public class BackUpDataTest {

    private BackupData backupData;

    @Before
    public void cleanUp() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/thirdtask", "root", "clouway.com");
            backupData = new BackupData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        backupData.deleteCustomers();
        backupData.deleteCustomersBackup();
    }

    @After
    public void disconnect() {
        backupData.closeConnection();
    }

    @Test
    public void happyPath() {
        Customer customer = new Customer(1, "Georgi Georgiev", "+359883497253", "ggeorgiev@abv.bg");
        Customer updatedCustomer = new Customer(1, "Ivan Ivanov", "+359883497253", "iivanov@abv.bg");

        backupData.register(customer);
        List<Customer> customers = backupData.findAll();
        List<Customer> expected = new ArrayList<Customer>();
        expected.add(customer);
        assertThat(expected, is(customers));

        backupData.update(updatedCustomer);
        List<Customer> customers2 = backupData.findAll();
        List<Customer> expected2 = Lists.newArrayList(updatedCustomer);

        assertThat(expected2, is(customers2));

        List<Customer> customersBackUp = backupData.findAllBackUp();
        assertThat(customersBackUp, is(expected));


    }
}

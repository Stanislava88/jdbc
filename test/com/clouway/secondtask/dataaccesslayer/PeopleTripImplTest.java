package com.clouway.secondtask.dataaccesslayer;

import com.clouway.secondtask.core.Person;
import com.clouway.secondtask.core.Trip;
import com.google.common.collect.Lists;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by clouway on 16-2-25.
 */
public class PeopleTripImplTest {
    private PeopleTripJdbcImpl peopleTripJdbc;

    @Before
    public void cleanUp() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/secondtask", "root", "clouway.com");
            peopleTripJdbc = new PeopleTripJdbcImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        peopleTripJdbc.deleteAll();
        peopleTripJdbc.delete();
    }

    @Test
    public void updatePerson() throws Exception {
        Person person = new Person("Dimityr Petrov", "8104221534", 33, "dpetrov@abv.bg");

        peopleTripJdbc.create(new Person("Grigor Dimitrov", "8104221534", 25, "gdimitrov@abv.bg"));

        peopleTripJdbc.update(person);

        List<Person> actual = peopleTripJdbc.findAll();
        List<Person> expected = Lists.newArrayList(person);

        assertThat(actual, is(expected));
    }

    @Test
    public void updateTrip() throws Exception {
        DateFormat date = new SimpleDateFormat("yyyyy-MM-dd");

        Person person = new Person("Stefan Roglev", "9102142210", 25, "sroglev@abv.bg");
        peopleTripJdbc.create(person);

        Trip trip = new Trip("9102142210", date.parse("2016-07-07"), date.parse("2016-08-08"), "Bansko");
        peopleTripJdbc.create(trip);

        Trip trip2 = new Trip("9102142210", date.parse("2016-09-09"), date.parse("2016-10-10"), "Bansko");
        peopleTripJdbc.update(trip2);

        List<Trip> actual = peopleTripJdbc.getAll();
        List<Trip> expected =  Lists.newArrayList(trip2);

        assertThat(actual, is(expected));
    }

    @Test
    public void showAllPeople() {
        Person person = new Person("Stefan Roglev", "9802142210", 25, "sroglev@abv.bg");
        peopleTripJdbc.create(person);

        Person person2 = new Person("Stefan Stefanov", "9202142210", 24, "sstefanov@abv.bg");
        peopleTripJdbc.create(person2);

        List<Person> actual = peopleTripJdbc.findAll();

        assertThat(actual.size(), is(2));
        assertThat(actual.contains(person), is(true));
        assertThat(actual.contains(person2), is(true));
    }

    @After
    public void disconnecT() {
        peopleTripJdbc.closeConnection();
    }
}

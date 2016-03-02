package com.clouway.firsttask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by clouway on 16-3-1.
 */
public class Tests {


    private PersonDAO personDAO;

    @Before
    public void connectToDatabaseAndCleanUp() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/firsttask", "root", "clouway.com");
            personDAO = new PersonDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        personDAO.deleteAll();
    }

    @After
    public void disconnect() {
        personDAO.closeConnection();
    }


    @Test
    public void findAll() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDAO.register(myperson);
        Person myperson2 = new Person("9209232201", "Ivan Ivanov", 23, "male");
        personDAO.register(myperson2);
        List<Person> got = personDAO.findAll();
        List<Person> want = new ArrayList<Person>();
        want.add(myperson);
        want.add(myperson2);
        assertThat(got.size(), is(2));
        assertThat(got, is(want));
    }

    @Test
    public void update() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDAO.register(myperson);
        Person myperson2 = new Person("9109232202", "Ivan Ivanov", 23, "male");
        personDAO.update(myperson2);
        List<Person> tableContain = personDAO.findAll();
        List<Person> expected = new ArrayList<Person>();
        expected.add(myperson2);
        assertThat(tableContain, is(expected));
    }

    @Test
    public void delete() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDAO.register(myperson);
        List<Person> actual = personDAO.findAll();
        assertThat(actual.size(), is(1));
        personDAO.deleteByEgn("9109232202");
        List<Person> actual2 = personDAO.findAll();
        assertThat(actual2.size(), is(0));
    }


    @Test
    public void findByEgn() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDAO.register(myperson);
        Person foundByEgn = personDAO.findByEgn("9109232202");
        assertThat(myperson, is(foundByEgn));
    }

    @Test
    public void like() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDAO.register(myperson);
        Person myperson2 = new Person("9109232203", "Krasimir Ivanov", 24, "male");
        personDAO.register(myperson2);
        Person myperson3 = new Person("9109232204", "Stefan Petkov", 24, "male");
        personDAO.register(myperson3);
        List<Person> got = personDAO.like("name", "Kr%");
        List<Person> want = new ArrayList<Person>();
        want.add(myperson);
        want.add(myperson2);
        assertThat(got.size(), is(2));
        assertThat(got, is(want));
    }
}

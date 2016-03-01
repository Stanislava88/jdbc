package com.clouway.firsttask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by clouway on 16-3-1.
 */
public class Tests {
    private PersonDao personDao = new PersonDao("jdbc:mysql://localhost/firsttask", "root", "clouway.com");

    @Before
    public void connectToDatabaseAndCleanUp() {
        personDao.connectToDatabase();
        personDao.deleteTableContent();
    }

    @After
    public void disconnect() {
        personDao.closeConnection();
    }


    @Test
    public void registerPerson() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDao.register(myperson);
        List<Person> tableContaint = personDao.findAll();
        assertThat(tableContaint.contains(myperson), is(true));
    }

    @Test
    public void findAll() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDao.register(myperson);
        Person myperson2 = new Person("9209232201", "Ivan Ivanov", 23, "male");
        personDao.register(myperson2);
        List<Person> tableContain = personDao.findAll();
        assertThat(tableContain.size(), is(2));
    }

    @Test
    public void update() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDao.register(myperson);
        Person myperson2 = new Person("9109232202", "Ivan Ivanov", 23, "male");
        personDao.update(myperson2);
        List<Person> tableContain = personDao.findAll();
        List<Person> expected = new ArrayList<Person>();
        expected.add(myperson2);
        assertThat(tableContain, is(expected));
    }

    @Test
    public void delete() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDao.register(myperson);
        List<Person> actual = personDao.findAll();
        assertThat(actual.size(), is(1));
        personDao.delete("9109232202");
        List<Person> actual2 = personDao.findAll();
        assertThat(actual2.size(), is(0));
    }


    @Test
    public void findByEgn() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDao.register(myperson);
        Person foundByEgn = personDao.findByEgn("9109232202");
        assertThat(myperson, is(foundByEgn));
    }

    @Test
    public void like() {
        Person myperson = new Person("9109232202", "Kristiyan Petkov", 24, "male");
        personDao.register(myperson);
        Person myperson2 = new Person("9109232203", "Krasimir Ivanov", 24, "male");
        personDao.register(myperson2);
        Person myperson3 = new Person("9109232204", "Stefan Petkov", 24, "male");
        personDao.register(myperson3);
        List<Person> likeWildcard = personDao.like("name", "Kr%");
        assertThat(likeWildcard.size(), is(2));
    }


}

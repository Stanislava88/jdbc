package com.clouway.jdbc.task2;

import com.clouway.task2.domain.Person;
import com.clouway.task2.adapter.PersistentPersonRepository;
import com.clouway.jdbc.task2.util.DatabaseCleaner;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentPersonRepositoryTest {
    private MysqlConnectionPoolDataSource dataSource=new MysqlConnectionPoolDataSource();
    @Before
    public void cleanUp() {
        dataSource.setURL("jdbc:mysql://localhost:3306/task2");
        dataSource.setUser("root");
        dataSource.setPassword("clouway.com");
        new DatabaseCleaner(dataSource,"people").cleanUp();
    }

    @Test
    public void emptyRepository() {
        PersistentPersonRepository userRepository = null;
        userRepository = new PersistentPersonRepository(dataSource);
        List<Person> users = userRepository.allPeopleInRepository();

        assertThat(users.size(), is(equalTo(0)));
    }

    @Test
    public void getStoredUser() {
        PersistentPersonRepository userRepository = null;
        userRepository = new PersistentPersonRepository(dataSource);
        userRepository.register(new Person(123456789,"ivan",23,"dasas@abv.bg"));
        List<Person> users = userRepository.allPeopleInRepository();

        assertThat(users.size(), is(equalTo(1)));
    }

    @Test
    public void updateTest() {
        PersistentPersonRepository userRepository = null;
        userRepository = new PersistentPersonRepository(dataSource);
        userRepository.register(new Person(123456789,"ivan",23,"dasas@abv.bg"));
        List<Person> users = userRepository.allPeopleInRepository();

        assertThat(users.get(0).getName(), is(equalTo("ivan")));

        userRepository.updatePerson(new Person(123456789, "petyr",21,"fhfiwf@abd.vf"));
        users = userRepository.allPeopleInRepository();

        assertThat(users.get(0).getName(), is(equalTo("petyr")));
    }
}

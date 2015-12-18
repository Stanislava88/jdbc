package com.clouway.jdbc.task1;

import com.clouway.jdbc.task1.util.DatabaseCleaner;
import com.clouway.task1.PersistentUserRepository;
import com.clouway.task1.User;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class UserRepositoryTest {
    private MysqlConnectionPoolDataSource dataSource=new MysqlConnectionPoolDataSource();
    @Before
    public void cleanUp() {
        dataSource.setURL("jdbc:mysql://localhost:3306/test");
        dataSource.setUser("root");
        dataSource.setPassword("clouway.com");
        new DatabaseCleaner("users").cleanUp();
    }

    @Test
    public void emptyRepository() {
        PersistentUserRepository userRepository = null;
        userRepository = new PersistentUserRepository(dataSource);
        List<User> users = userRepository.getUsers();

        assertThat(users.size(), is(equalTo(0)));
    }

    @Test
    public void getUserThatWasStored() {
        PersistentUserRepository userRepository = null;
        userRepository = new PersistentUserRepository(dataSource);
        userRepository.register(new User("johnsmith@gmail.com","John Smith"));

        List<User> users = userRepository.getUsers();
        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).email, is(equalTo("johnsmith@gmail.com")));
    }

    @Test
    public void getAnotherUserThatWasStored() {
        PersistentUserRepository userRepository = null;
        userRepository = new PersistentUserRepository(dataSource);
        userRepository.register(new User("slavidichkov@gmail.com","Slavi Dichkov"));

        List<User> users = userRepository.getUsers();
        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).name, is(equalTo("Slavi Dichkov")));
    }

    @Test
    public void deleteUserThatWasStored() {
        PersistentUserRepository persistentUserRepository=null;
        persistentUserRepository=new PersistentUserRepository(dataSource);
        int id=persistentUserRepository.register(new User("ivangeorgiev@gmail.com","Ivan Georgiev"));

        List<User> users = persistentUserRepository.getUsers();
        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).name, is(equalTo("Ivan Georgiev")));

        User user=persistentUserRepository.delete(id);
        assertThat(user.email, is(equalTo("ivangeorgiev@gmail.com")));
    }

    @Test
    public void updateUserThatWasStored() {
        PersistentUserRepository persistentUserRepository=null;
        persistentUserRepository=new PersistentUserRepository(dataSource);
        int id=persistentUserRepository.register(new User("ivangeorgiev@gmail.com","Ivan Georgiev"));

        List<User> users = persistentUserRepository.getUsers();
        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).name, is(equalTo("Ivan Georgiev")));

        persistentUserRepository.updateUser(id,"nikola","nikolaev@abv.bg");

        users = persistentUserRepository.getUsers();
        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).name, is(equalTo("nikola")));
    }

    @Test
    public void searchUserThatWasStored() {
        PersistentUserRepository persistentUserRepository=null;
        persistentUserRepository=new PersistentUserRepository(dataSource);
        persistentUserRepository.register(new User("ivangeorgiev@gmail.com","Ivan Georgiev"));
        persistentUserRepository.register(new User("ana@gmail.com","ana ivanova"));

        List<User> users = persistentUserRepository.getUsers();
        assertThat(users.size(), is(equalTo(2)));
        assertThat(users.get(0).name, is(equalTo("Ivan Georgiev")));

        users = persistentUserRepository.findUsers("an");
        assertThat(users.size(), is(equalTo(2)));
        assertThat(users.get(0).name, is(equalTo("Ivan Georgiev")));
    }
}

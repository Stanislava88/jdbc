package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.PeopleRepository;
import com.clouway.tripagency.core.Provider;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentPeopleRepositoryTest {
  private PeopleRepository repository;
  private Connection connection;

  @Before
  public void setUp() throws Exception {
    Provider<Connection> provider = new ConnectionProvider();
    repository = new PersistentPeopleRepository(provider);
    connection = provider.get();

    PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE people CASCADE");
    preparedStatement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    connection.close();
  }

  @Test
  public void happyPath() throws Exception {
    Person person = new Person("Ivan", "9008082020", 26, "ivan@abv.bg");
    repository.register(person);

    Person actual = repository.findByEgn("9008082020");

    assertThat(actual, is(person));
  }

  @Test
  public void findUnregisterPerson() throws Exception {
    Person actual = repository.findByEgn("8804067070");

    assertThat(actual, is(equalTo(null)));
  }

  @Test
  public void updateByEgn() throws Exception {
    Person person = new Person("Maria", "0000000000", 30, "maria@abv.bg");
    repository.register(person);

    repository.updateByEgn(person.egn, new Person(person.name, person.egn, person.age, "maria20@abv.bg"));

    Person actual = repository.findByEgn("0000000000");
    Person expected = new Person("Maria", "0000000000", 30, "maria20@abv.bg");

    assertThat(actual, is(expected));
  }

  @Test
  public void findAll() throws Exception {
    Person person1 = new Person("Lilia", "1111111111", 20, "lilia@abv.bg");
    Person person2 = new Person("Milena", "2222222222", 22, "milena@abv.bg");

    repository.register(person1);
    repository.register(person2);

    List<Person> actual = repository.findAll();
    List<Person> expected = Lists.newArrayList(person1, person2);

    assertThat(actual, is(expected));
  }

  @Test
  public void findMatching() throws Exception {
    Person person1 = new Person("Michaela", "1111111111", 20, "michaela@abv.bg");
    Person person2 = new Person("Milena", "2222222222", 22, "milena@abv.bg");
    Person person3 = new Person("Victor", "3333333333", 28, "victor@abv.bg");

    repository.register(person1);
    repository.register(person2);
    repository.register(person3);

    List<Person> actual = repository.findMatching("Mi");
    List<Person> expected = Lists.newArrayList(person1, person2);

    assertThat(actual, is(expected));
  }
}

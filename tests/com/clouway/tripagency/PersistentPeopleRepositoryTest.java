package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.core.People;
import com.clouway.tripagency.core.PeopleRepository;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentPeopleRepositoryTest {
  private Connection connection;
  private PeopleRepository repository;

  @Before
  public void setUp() throws Exception {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost/tripagency", "postgres", "clouway.com");
    repository = new PersistentPeopleRepository(connection);

    PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE people,trip");
    preparedStatement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    connection.close();
  }

  @Test
  public void happyPath() throws Exception {
    People people = new People("Ivan", "9008082020", 26, "ivan@abv.bg");
    repository.register(people);

    People actual = repository.findByEgn("9008082020");

    assertThat(actual, is(people));
  }

  @Test
  public void updateEmailByEgn() throws Exception {
    People people = new People("Maria", "0000000000", 30, "maria@abv.bg");
    repository.register(people);

    repository.updateEmailByEgn("maria20@abv.bg", "0000000000");

    People actual = repository.findByEgn("0000000000");
    People expected = new People("Maria", "0000000000", 30, "maria20@abv.bg");

    assertThat(actual, is(expected));
  }

  @Test
  public void findAll() throws Exception {
    People people1 = new People("Lilia", "1111111111", 20, "lilia@abv.bg");
    People people2 = new People("Milena", "2222222222", 22, "milena@abv.bg");

    repository.register(people1);
    repository.register(people2);

    List<People> actual = repository.findAll();
    List<People> expected = Lists.newArrayList(people1, people2);

    assertThat(actual, is(expected));
  }

  @Test
  public void findNameLikeSymbol() throws Exception {
    People people1 = new People("Michaela", "1111111111", 20, "michaela@abv.bg");
    People people2 = new People("Milena", "2222222222", 22, "milena@abv.bg");
    People people3 = new People("Victor", "3333333333", 28, "victor@abv.bg");

    repository.register(people1);
    repository.register(people2);
    repository.register(people3);

    List<People> actual = repository.findNamesLikeSymbol("Mi");
    List<People> expected = Lists.newArrayList(people1, people2);

    assertThat(actual, is(expected));
  }
}

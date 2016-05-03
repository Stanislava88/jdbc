package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Provider;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.PeopleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentPeopleRepository implements PeopleRepository {
  private Provider<Connection> provider;

  public PersistentPeopleRepository(Provider provider) {
    this.provider = provider;
  }

  @Override
  public void register(Person person) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO people VALUES (?,?,?,?)")) {

      preparedStatement.setString(1, person.name);
      preparedStatement.setString(2, person.egn);
      preparedStatement.setInt(3, person.age);
      preparedStatement.setString(4, person.email);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }

  @Override
  public Person findByEgn(String egn) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM people WHERE egn=?")) {
      preparedStatement.setString(1, egn);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");

        return new Person(name, egn, age, email);
      }
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public void updateByEgn(String egn, Person person) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("UPDATE people SET name=?,email=?,age=? WHERE egn=?")) {

      preparedStatement.setString(1, person.name);
      preparedStatement.setString(2, person.email);
      preparedStatement.setInt(3, person.age);
      preparedStatement.setString(4, egn);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }

  @Override
  public List<Person> findAll() {
    List<Person> people = new ArrayList<>();

    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM people")) {

      getResultSet(people, preparedStatement);
    } catch (SQLException e) {
    }
    return people;
  }

  @Override
  public List<Person> findMatching(String pattern) {
    List<Person> people = new ArrayList<>();

    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM people WHERE name LIKE ?")) {
      preparedStatement.setString(1, pattern + "%");

      getResultSet(people, preparedStatement);
    } catch (SQLException e) {
    }
    return people;
  }

  private void getResultSet(List<Person> people, PreparedStatement preparedStatement) {
    ResultSet resultSet = null;
    try {
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {

        String name = resultSet.getString("name");
        String egn = resultSet.getString("egn");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");

        people.add(new Person(name, egn, age, email));
      }
    } catch (SQLException e) {
    }
  }
}
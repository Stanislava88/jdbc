package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.People;
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
  private Connection connection;

  public PersistentPeopleRepository(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void register(People people) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO people VALUES (?,?,?,?)")) {
      preparedStatement.setString(1, people.name);
      preparedStatement.setString(2, people.egn);
      preparedStatement.setInt(3, people.age);
      preparedStatement.setString(4, people.email);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public People findByEgn(String egn) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM people WHERE egn=?")) {
      preparedStatement.setString(1, egn);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");

        return new People(name, egn, age, email);
      }
      return null;
    }
  }

  @Override
  public void updateEmailByEgn(String email, String egn) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE people SET email=? WHERE egn=?")) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, egn);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public List<People> findAll() throws SQLException {
    List<People> peoples = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM people")) {
      getResultSet(peoples, preparedStatement);
    }
    return peoples;
  }

  @Override
  public List<People> findNamesLikeSymbol(String symbol) throws SQLException {
    List<People> peoples = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM people WHERE name LIKE ?")) {
      preparedStatement.setString(1, symbol + "%");
      getResultSet(peoples, preparedStatement);
    }
    return peoples;
  }

  private void getResultSet(List<People> peoples, PreparedStatement preparedStatement) throws SQLException {
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {

      String name = resultSet.getString("name");
      String egn = resultSet.getString("egn");
      int age = resultSet.getInt("age");
      String email = resultSet.getString("email");
      peoples.add(new People(name, egn, age, email));
    }
  }
}

package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Provider;
import com.clouway.users.core.User;
import com.clouway.users.core.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {
  Provider<Connection> provider;

  public PersistentUserRepository(Provider provider) {
    this.provider = provider;
  }

  @Override
  public void register(User user) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO users(id,firstName,lastName,age)VALUES(?,?,?,?) ")) {

      preparedStatement.setInt(1, user.id);
      preparedStatement.setString(2, user.firstName);
      preparedStatement.setString(3, user.lastName);
      preparedStatement.setInt(4, user.age);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }

  @Override
  public User findById(int id) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM users WHERE id=?")) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        int age = resultSet.getInt("age");

        return new User(id, firstName, lastName, age);
      }
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public List<User> findAll() {
    List<User> users = new ArrayList<User>();
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM users")) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        int age = resultSet.getInt("age");

        users.add(new User(id, firstName, lastName, age));
      }
    } catch (SQLException e) {
    }
    return users;
  }
}

package com.clouway.index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Demo {
  public static void main(String[] args) {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/store", "postgres", "clouway.com");
         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer(id,firstName,lastName,egn,phoneNumber) VALUES (?,?,?,?,?)")) {

      connection.setAutoCommit(false);

      for (int i = 0; i < 1000000; i++) {

        preparedStatement.setInt(1, i);
        preparedStatement.setString(2, "firstName" + i);
        preparedStatement.setString(3, "lastName" + i);
        preparedStatement.setString(4, "egn" + i);
        preparedStatement.setString(5, "phoneNumber" + i);

        preparedStatement.execute();
      }

      connection.commit();
    } catch (SQLException e) {
    }
  }
}

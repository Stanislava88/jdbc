package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class ConnectionProvider implements Provider {
  private String database = "jdbc:postgresql://localhost/store";
  private String user = "postgres";
  private String pass = "clouway.com";

  @Override
  public Connection provide() {
    Connection connection = null;
    try {

      return connection = DriverManager.getConnection(database, user, pass);

    } catch (SQLException e) {
      try {

        connection.close();
      } catch (SQLException e1) {
      }
    }
    return null;
  }
}

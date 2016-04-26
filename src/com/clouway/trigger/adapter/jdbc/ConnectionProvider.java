package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.Provider;

import java.security.ProviderException;
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
  private Connection connection;

  @Override
  public Object provide() {
    try {
      return connection = DriverManager.getConnection(database, user, pass);
    } catch (SQLException e) {
      throw new ProviderException();
    }
  }

  @Override
  public void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}

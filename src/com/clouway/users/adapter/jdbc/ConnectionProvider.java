package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class ConnectionProvider implements Provider {
  private final String database = "jdbc:postgresql://localhost/userinformation";
  private final String user = "postgres";
  private final String pass = "clouway.com";

  @Override
  public Connection get() {
    try {
      return  DriverManager.getConnection(database, user, pass);
    } catch (SQLException e) {}

    return null;
  }
}

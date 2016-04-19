package com.clouway.sqlcommands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class DDLCommand {
  private Connection connection;
  private PreparedStatement statement;

  @Before
  public void setUp() throws Exception {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost/university", "postgres", "clouway.com");

    statement = connection.prepareStatement("CREATE TABLE table1(id int, name text,phone int)");
    statement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    statement = connection.prepareStatement("drop table table1");
    statement.executeUpdate();

    statement.close();
    connection.close();
  }

  @Test
  public void alter() throws Exception {
    statement = connection.prepareStatement("Alter TABLE table1 add COLUMN egn int");
    statement.executeUpdate();

    int numberRow = countRow();

    assertThat(numberRow, is(4));
  }

  private int countRow() throws Exception {
    ResultSet resultSet = connection.createStatement().executeQuery("select * from table1");
    ResultSetMetaData rsmd = resultSet.getMetaData();

    return rsmd.getColumnCount();
  }
}

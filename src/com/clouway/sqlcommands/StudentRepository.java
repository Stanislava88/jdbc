package com.clouway.sqlcommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class StudentRepository {
  private Connection connection;

  public StudentRepository(Connection connection) {
    this.connection = connection;
  }

  public void register(Student student) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("INSERT  into students VALUES (?,?,?,?)");

    statement.setInt(1, student.id);
    statement.setString(2, student.first_name);
    statement.setString(3, student.last_name);
    statement.setInt(4, student.age);

    statement.executeUpdate();
  }

  public List<Student> selectAll() throws SQLException {
    List<Student> students = new ArrayList<Student>();

    PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {

      int id = resultSet.getInt("id");
      String first_name = resultSet.getString("first_name");
      String last_name = resultSet.getString("last_name");
      int age = resultSet.getInt("age");

      students.add(new Student(id, first_name, last_name, age));
    }
    return students;
  }

  public void update(Student student) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("Update students set age=? where id=?");

    preparedStatement.setInt(1, student.age);
    preparedStatement.setInt(2, student.id);

    preparedStatement.executeUpdate();
  }

  public void delete(int id) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("DELETE from students WHERE id=?");
    preparedStatement.setInt(1, id);

    preparedStatement.executeUpdate();
  }
}

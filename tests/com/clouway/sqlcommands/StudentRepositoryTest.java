package com.clouway.sqlcommands;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class StudentRepositoryTest {
  private StudentRepository repository;
  private Connection connection;

  @Before
  public void setUp() throws Exception {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost/university", "postgres", "clouway.com");
    repository = new StudentRepository(connection);

    PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE students");
    preparedStatement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    connection.close();
  }

  @Test
  public void happyPath() throws Exception {
    Student student = new Student(1, "Ivan", "Ivanov", 23);
    repository.register(student);

    Student actual = repository.findByID(1);

    assertThat(student, is(actual));
  }

  @Test
  public void registerMultiple() throws Exception {
    Student student1 = new Student(1, "Lilia", "Angelova", 24);
    Student student2 = new Student(2, "Katia", "Ivanova", 25);

    repository.register(student1);
    repository.register(student2);

    List<Student> actual = repository.findAll();
    List<Student> expected = Lists.newArrayList(student1, student2);

    assertThat(expected, is(actual));
  }

  @Test
  public void findByID() throws Exception {
    Student student = new Student(1, "Lilia", "Angelova", 24);

    repository.register(student);

    Student expected = repository.findByID(1);

    assertThat(expected, is(student));
  }

  @Test
  public void findUnregisterStudent() throws Exception {
    Student expected = repository.findByID(1);

    assertThat(expected, is(equalTo(null)));
  }

  @Test
  public void updateAgeByID() throws Exception {
    Student student = new Student(1, "Lilia", "Angelova", 24);

    repository.register(student);
    repository.updateAgeByID(28, 1);

    Student actual = repository.findByID(1);
    Student expected = new Student(1, "Lilia", "Angelova", 28);

    assertThat(actual, is(expected));
  }

  @Test
  public void delete() throws Exception {
    Student student1 = new Student(1, "Lilia", "Angelova", 24);
    Student student2 = new Student(2, "Ivan", "Ivanov", 23);

    repository.register(student1);
    repository.register(student2);
    repository.deleteByID(2);

    List<Student> actual = repository.findAll();
    List<Student> expected = Lists.newArrayList(student1);

    assertThat(actual, is(expected));
  }
}

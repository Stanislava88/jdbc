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

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class StudentRepositoryTest {
  private StudentRepository studentRepository;
  private Connection connection;

  @Before
  public void setUp() throws Exception {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost/university", "postgres", "clouway.com");
    studentRepository = new StudentRepository(connection);
  }

  @After
  public void tearDown() throws Exception {
    PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE students");
    preparedStatement.executeUpdate();

    connection.close();
  }

  @Test
  public void happyPath() throws Exception {
    Student student = new Student(1, "Ivan", "Ivanov", 23);
    studentRepository.register(student);

    List<Student> actual = studentRepository.selectAll();
    List<Student> expected = Lists.newArrayList(student);

    assertThat(expected, is(actual));
  }

  @Test
  public void registerAnotherStudent() throws Exception {
    Student student1 = new Student(1, "Lilia", "Angelova", 24);
    Student student2 = new Student(2, "Katia", "Ivanova", 25);

    studentRepository.register(student1);
    studentRepository.register(student2);

    List<Student> actual = studentRepository.selectAll();
    List<Student> expected = Lists.newArrayList(student1, student2);

    assertThat(expected, is(actual));
  }

  @Test
  public void update() throws Exception {
    Student student = new Student(1, "Lilia", "Angelova", 24);

    studentRepository.register(student);
    studentRepository.update(new Student(1, "Lilia", "Angelova", 28));

    List<Student> actual = studentRepository.selectAll();
    List<Student> expected = Lists.newArrayList(new Student(1, "Lilia", "Angelova", 28));

    assertThat(actual, is(expected));
  }

  @Test
  public void delete() throws Exception {
    Student student1 = new Student(1, "Lilia", "Angelova", 24);
    Student student2 = new Student(2, "Ivan", "Ivanov", 23);

    studentRepository.register(student1);
    studentRepository.register(student2);
    studentRepository.delete(2);

    List<Student> actual = studentRepository.selectAll();
    List<Student> expected = Lists.newArrayList(student1);

    assertThat(actual, is(expected));

  }
}

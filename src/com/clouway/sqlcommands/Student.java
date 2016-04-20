package com.clouway.sqlcommands;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Student {
  public final int id;
  public final String firstName;
  public final String lastName;
  public final int age;

  public Student(int id, String firstName, String lastName, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    if (id != student.id) return false;
    if (age != student.age) return false;
    if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
    return lastName != null ? lastName.equals(student.lastName) : student.lastName == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + age;
    return result;
  }
}

package com.clouway.sqlcommands;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Student {
  public final int id;
  public final String first_name;
  public final String last_name;
  public final int age;

  public Student(int id, String first_name, String last_name, int age) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    if (id != student.id) return false;
    if (age != student.age) return false;
    if (first_name != null ? !first_name.equals(student.first_name) : student.first_name != null) return false;
    return last_name != null ? last_name.equals(student.last_name) : student.last_name == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
    result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
    result = 31 * result + age;
    return result;
  }
}

package com.clouway.users.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class User {
  public final int id;
  public final String firstName;
  public final String lastName;
  public final int age;

  public User(int id, String firstName, String name, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = name;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    if (age != user.age) return false;
    return lastName != null ? lastName.equals(user.lastName) : user.lastName == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + age;
    return result;
  }
}

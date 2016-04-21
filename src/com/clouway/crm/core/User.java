package com.clouway.crm.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class User {
  public final int id;
  public final String name;
  public final int age;

  public User(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    if (age != user.age) return false;
    return name != null ? name.equals(user.name) : user.name == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + age;
    return result;
  }
}

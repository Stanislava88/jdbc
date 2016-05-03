package com.clouway.tripagency.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Person {
  public final String name;
  public final String egn;
  public final int age;
  public final String email;

  public Person(String name, String egn, int age, String email) {
    this.name = name;
    this.egn = egn;
    this.age = age;
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (age != person.age) return false;
    if (!name.equals(person.name)) return false;
    if (!egn.equals(person.egn)) return false;
    return email.equals(person.email);

  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + egn.hashCode();
    result = 31 * result + age;
    result = 31 * result + email.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
            "name='" + name + '\'' +
            ", egn='" + egn + '\'' +
            ", age=" + age +
            ", email='" + email + '\'' +
            '}';
  }
}

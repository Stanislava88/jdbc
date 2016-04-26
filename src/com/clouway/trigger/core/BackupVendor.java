package com.clouway.trigger.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class BackupVendor {
  private final int id;
  private final String firstName;
  private final String lastName;
  private final int age;

  public BackupVendor(int id, String firstName, String lastName, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BackupVendor that = (BackupVendor) o;

    if (id != that.id) return false;
    if (age != that.age) return false;
    if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
    return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + age;
    return result;
  }

  @Override
  public String toString() {
    return "BackupVendor{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", age=" + age +
            '}';
  }
}

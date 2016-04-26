package com.clouway.trigger.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Vendor {
  public final int id;
  public final String firstName;
  public final String lastName;
  public final int age;

  public Vendor(int id, String firstName, String lastName, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vendor vendor = (Vendor) o;

    if (id != vendor.id) return false;
    if (age != vendor.age) return false;
    if (firstName != null ? !firstName.equals(vendor.firstName) : vendor.firstName != null) return false;
    return lastName != null ? lastName.equals(vendor.lastName) : vendor.lastName == null;

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

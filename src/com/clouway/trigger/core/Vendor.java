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
}

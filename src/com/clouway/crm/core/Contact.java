package com.clouway.crm.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Contact {
  public final int userId;
  public final int addressId;

  public Contact(int user, int address) {
    this.userId = user;
    this.addressId = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Contact contact = (Contact) o;

    if (userId != contact.userId) return false;
    return addressId == contact.addressId;

  }

  @Override
  public int hashCode() {
    int result = userId;
    result = 31 * result + addressId;
    return result;
  }
}

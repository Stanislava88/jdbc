package com.clouway.users.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class NewContact {
  public final int id;
  public final int userId;
  public final int addressId;

  public NewContact(int id, int userId, int addressId) {
    this.id = id;
    this.userId = userId;
    this.addressId = addressId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NewContact newContact = (NewContact) o;

    if (id != newContact.id) return false;
    if (userId != newContact.userId) return false;
    return addressId == newContact.addressId;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + userId;
    result = 31 * result + addressId;
    return result;
  }
}

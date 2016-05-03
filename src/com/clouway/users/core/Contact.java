package com.clouway.users.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Contact {
  private final int id;
  private final User user;
  private final Address address;

  public Contact(int id, User user, Address address) {
    this.id = id;
    this.user = user;
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Contact that = (Contact) o;

    if (id != that.id) return false;
    if (user != null ? !user.equals(that.user) : that.user != null) return false;
    return address != null ? address.equals(that.address) : that.address == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    return result;
  }
}

package com.clouway.crm.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Address {
  public final int id;
  public final String city;
  public final String street;
  public final int number;

  public Address(int id, String city, String street, int number) {
    this.id = id;
    this.city = city;
    this.street = street;
    this.number = number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Address address = (Address) o;

    if (id != address.id) return false;
    if (number != address.number) return false;
    if (city != null ? !city.equals(address.city) : address.city != null) return false;
    return street != null ? street.equals(address.street) : address.street == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + number;
    return result;
  }
}

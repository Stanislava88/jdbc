package com.clouway.jdbc.info.users;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Address {
    public final int id;
    public final int userId;
    public final String address;

    public Address(int id, int userId, String address) {
        this.id = id;
        this.userId = userId;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (id != address1.id) return false;
        if (userId != address1.userId) return false;
        return !(address != null ? !address.equals(address1.address) : address1.address != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

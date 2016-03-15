package com.clouway.jdbc.info.users.persistence;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Address {
    public final long id;
    public final long userId;
    public final String address;

    public Address(long id, long userId, String address) {
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

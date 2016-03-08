package com.clouway.thirdtask;

/**
 * Created by clouway on 16-2-25.
 */
public class Customer {
    public final int id;
    public final String name;
    public final String phoneNumber;
    public final String email;

    public Customer(int id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(customer.phoneNumber) : customer.phoneNumber != null)
            return false;
        return !(email != null ? !email.equals(customer.email) : customer.email != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}

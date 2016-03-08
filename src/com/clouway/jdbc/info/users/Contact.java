package com.clouway.jdbc.info.users;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Contact {
    public final int id;
    public final int userId;
    public final String number;

    public Contact(int id, int userId, String number) {
        this.id = id;
        this.userId = userId;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (userId != contact.userId) return false;
        return !(number != null ? !number.equals(contact.number) : contact.number != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}

package com.clouway.jdbc.info.users.persistence;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Contact {
    public final long id;
    public final long userId;
    public final String number;

    public Contact(long id, long userId, String number) {
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}

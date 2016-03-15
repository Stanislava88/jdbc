package com.clouway.jdbc.info.users.persistence;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface ContactRepository {
    void add(Contact contact);

    Contact findById(long id);

    List<Contact> findAll();
}

package com.clouway.jdbc.info.users.persistence;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface AddressRepository {

    void add(Address address);

    Address findById(long id);

    List<Address> findAll();
}

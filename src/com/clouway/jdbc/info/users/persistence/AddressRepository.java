package com.clouway.jdbc.info.users.persistence;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface AddressRepository {

    void add(Address address);

    Address getById(int id);

    List<Address> getList();
}

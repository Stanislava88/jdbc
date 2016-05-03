package com.clouway.users.core;

import java.util.List;

/**
 * The implementation of this interface will be used to save and retrieve addresses
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface AddressRepository {
  /**
   * @param address registered addressId
   */
  void register(Address address);

  /**
   * Will return addressId by id
   *
   * @param id id for this addressId
   * @return addressId
   */
  Address findById(int id);

  /**
   * Will return list of all registered addresses
   *
   * @return list of addresses
   */
  List<Address> findAll();
}

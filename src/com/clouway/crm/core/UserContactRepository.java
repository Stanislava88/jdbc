package com.clouway.crm.core;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface UserContactRepository {
  void registerUser(User user) throws SQLException;

  User findById(int id) throws SQLException;

  List<User> findAllUsers() throws SQLException;

  void registerAddress(Address address1) throws SQLException;

  List<Address> findAllAddress() throws SQLException;

  void registerContact(Contact contact) throws SQLException;

  List<Contact> findAllContacts() throws SQLException;
}

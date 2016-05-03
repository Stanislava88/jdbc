package com.clouway.users.core;

import java.util.List;

/**
 * The implementation of this interface will be used to save and retrieve contacts
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface UserContactRepository {
  /**
   * @param newContact registered newContact
   */
  void register(NewContact newContact);

  /**
   * Will return contact
   *
   * @param id id for this contact
   * @return contact registered for this id
   */
  Contact findById(int id);

  /**
   * Will return list of all registered contacts
   *
   * @return list of contacts
   */
  List<Contact> findAll();
}

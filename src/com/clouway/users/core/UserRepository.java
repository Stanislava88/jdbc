package com.clouway.users.core;

import java.util.List;

/**
 * The implementation of this interface will bew used to save and retrieve users
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface UserRepository {
  /**
   * @param user registered userId
   */
  void register(User user);

  /**
   * Will return found userId at that id
   *
   * @param id id at the userId
   * @return userId
   */
  User findById(int id);

  /**
   * Will return list of all registered users
   *
   * @return list of users
   */
  List<User> findAll();
}

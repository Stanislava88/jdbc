package com.clouway.users.core;

/**
 * The implementation of this interface will be used to get resource
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface Provider<T> {
  /**
   * Will be return resource
   *
   * @return resource
   */
  T get();
}

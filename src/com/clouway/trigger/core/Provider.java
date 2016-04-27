package com.clouway.trigger.core;

/**
 * The implementation of this interface will be used to provide resource
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface Provider<T> {
  /**
   * Will return provided resource
   *
   * @return provided resource
   */
  T provide();

  /**
   * Will close resource
   */
  void close();
}

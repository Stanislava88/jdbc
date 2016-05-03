package com.clouway.tripagency.core;


/**
 * The implementation of this interface will be used to get resource
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface Provider<T> {
  /**
   * Will return provided resource
   *
   * @return resource
   */
  T get();
}
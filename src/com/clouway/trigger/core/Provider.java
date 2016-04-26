package com.clouway.trigger.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface Provider<T> {
  void close();

  T provide();
}

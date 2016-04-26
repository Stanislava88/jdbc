package com.clouway.trigger.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface VendorRepository {
  void register(Vendor vendor);

  Vendor findById(int id);

  void update(int id, Vendor vendor);
}

package com.clouway.trigger.core;

/**
 * The implementation ont his interface will be used to save and retrieve vendors
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface VendorRepository {
  /**
   * @param vendor registered vendor
   */
  void register(Vendor vendor);

  /**
   * Will return the found by this id vendor
   *
   * @param id id at the vendor
   * @return the found vendor
   */
  Vendor findById(int id);

  /**
   * @param id     id at the vendor
   * @param vendor vendor
   */
  void updateById(int id, Vendor vendor);
}

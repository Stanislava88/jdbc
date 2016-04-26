package com.clouway.trigger.core;

import java.util.List;

/**
 * The implementation of this interface will be used to save and retrieve backup for vendors
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface BackupVendorRepository {
  /**
   * Will return finding by id ald state for vendor
   *
   * @param id id at the vendor
   * @return backup for the vendor
   */
  Vendor findBackupById(int id);//todo findById

  /**
   * Will return list of all vendors before change
   *
   * @return list of backup vendors
   */
  List<BackupVendor> findAllBackups();
}

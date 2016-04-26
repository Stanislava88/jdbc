package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.BackupVendorRepository;
import com.clouway.trigger.core.Provider;

import java.sql.Connection;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentBackupVendorRepository implements BackupVendorRepository {
  private Provider<Connection> provider;

  public PersistentBackupVendorRepository(Provider<Connection> provider) {

    this.provider = provider;
  }
}

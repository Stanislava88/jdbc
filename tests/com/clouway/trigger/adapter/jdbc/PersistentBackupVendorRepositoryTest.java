package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.BackupVendorRepository;
import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.Vendor;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentBackupVendorRepositoryTest {
  private Provider<Connection> provider;
  private PreparedStatement preparedStatement;
  private BackupVendorRepository repository;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();

    repository = new PersistentBackupVendorRepository(provider);

    preparedStatement = provider.provide().prepareStatement("TRUNCATE vendor,backupVendor");
    preparedStatement.executeUpdate();
  }

  @Test
  public void happyPath() throws Exception {


  }

  private
}

package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.Vendor;
import com.clouway.trigger.core.VendorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentVendorRepositoryTest {
  private Provider provider;
  private PreparedStatement preparedStatement;
  private VendorRepository repository;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();

    repository = new PersistentVendorRepository(provider);

  }

  @After
  public void tearDown() throws Exception {
    provider.close();
  }

  @Test
  public void happyPath() throws Exception {
    Vendor vendor = new Vendor(1, "Maria", "Petrova",20);

  }
}

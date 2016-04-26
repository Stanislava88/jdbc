package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.Vendor;
import com.clouway.trigger.core.VendorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentVendorRepositoryTest {
  private Provider<Connection> provider;
  private PreparedStatement preparedStatement;
  private VendorRepository repository;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();

    repository = new PersistentVendorRepository(provider);

    preparedStatement = provider.provide().prepareStatement("TRUNCATE vendor");
    preparedStatement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    provider.close();
  }

  @Test
  public void happyPath() throws Exception {
    Vendor vendor = new Vendor(1, "Maria", "Petrova", 20);

    repository.register(vendor);

    Vendor actual = repository.findById(1);

    assertThat(actual, is(vendor));
  }

  @Test
  public void update() throws Exception {
    Vendor vendor = new Vendor(1, "Marina", "Ivanova", 25);
    Vendor updatedVendor = new Vendor(1, "Maria", "Ivanova", 24);

    repository.register(vendor);
    repository.update(1,updatedVendor);

    Vendor actual = repository.findById(1);

    assertThat(actual, is(updatedVendor));
  }
}

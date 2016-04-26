package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.VendorRepository;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentVendorRepository implements VendorRepository {
  private Provider provider;

  public PersistentVendorRepository(Provider provider) {
    this.provider = provider;
  }
}

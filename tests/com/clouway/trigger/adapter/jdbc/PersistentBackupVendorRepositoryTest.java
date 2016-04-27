package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.BackupVendorRepository;
import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.Vendor;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

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

    cleanUp();
  }

  @Test
  public void happyPath() throws Exception {
    Vendor vendor = new Vendor(1, "Lilia", "angelova", 18);
    insertInVendor(vendor);

    Vendor updatedVendor = new Vendor(1, "Lilia", "angelova", 20);
    updateVendor(1, updatedVendor);

    Vendor actual = repository.findById(1);

    assertThat(actual, is(vendor));
  }

  @Test
  public void findBackupNoChange() throws Exception {
    Vendor actual = repository.findById(1);

    assertThat(actual, is(equalTo(null)));
  }

  @Test
  public void findAllBackups() throws Exception {
    Vendor vendor1 = new Vendor(1, "Lilia", "angelova", 18);
    Vendor vendor2 = new Vendor(2, "Marina", "Ivanova", 20);
    Vendor vendor3 = new Vendor(3, "Ivan", "Petrov", 22);

    insertInVendor(vendor1, vendor2, vendor3);

    updateVendor(1, new Vendor(1, "Lilia", "angelova", 18));
    updateVendor(3, new Vendor(3, "Ivan", "Petrov", 26));

    List<Vendor> actual = repository.findAll();
    List<Vendor> expected = Lists.newArrayList(vendor1, vendor3);

    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findVendorChangeTwice() throws Exception {
    Vendor vendor1 = new Vendor(1, "Lilia", "angelova", 18);
    insertInVendor(vendor1);

    updateVendor(1, new Vendor(1, "Lilia", "angelova", 20));
    updateVendor(1, new Vendor(1, "Petyr", "Petrov", 25));

    List<Vendor> actual = repository.findAll();
    List<Vendor> expected = Lists.newArrayList(new Vendor(1, "Lilia", "angelova", 18), new Vendor(1, "Lilia", "angelova", 20));

    assertThat(actual, is(expected));
  }


  private void insertInVendor(Vendor... vendors) {
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("INSERT INTO vendor VALUES(?,?,?,?)")) {

      for (Vendor vendor : vendors) {
        preparedStatement.setInt(1, vendor.id);
        preparedStatement.setString(2, vendor.firstName);
        preparedStatement.setString(3, vendor.lastName);
        preparedStatement.setInt(4, vendor.age);

        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void updateVendor(int id, Vendor vendor) {
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("UPDATE vendor set firstName=?,lastName=?,age=? WHERE id=?")) {
      preparedStatement.setString(1, vendor.firstName);
      preparedStatement.setString(2, vendor.lastName);
      preparedStatement.setInt(3, vendor.age);
      preparedStatement.setInt(4, id);

      preparedStatement.executeUpdate();
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
  }

  private void cleanUp() throws Exception {
    preparedStatement = provider.provide().prepareStatement("TRUNCATE vendor,backupVendor");
    preparedStatement.executeUpdate();
  }
}

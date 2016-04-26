package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.BackupVendor;
import com.clouway.trigger.core.BackupVendorRepository;
import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentBackupVendorRepository implements BackupVendorRepository {
  private Provider<Connection> provider;

  public PersistentBackupVendorRepository(Provider<Connection> provider) {
    this.provider = provider;
  }

  @Override
  public Vendor findBackupById(int id) {
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("SELECT * FROM backupVendor WHERE idvendor=?")) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        int age = resultSet.getInt("age");

        return new Vendor(id, firstName, lastName, age);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<BackupVendor> findAllBackups() {
    List<BackupVendor> vendors = new ArrayList<>();
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("SELECt * FROM  backupVendor")) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int id = resultSet.getInt("idvendor");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        int age = resultSet.getInt("age");

        vendors.add(new BackupVendor(id, firstName, lastName, age));
      }

      return vendors;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}

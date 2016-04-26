package com.clouway.trigger.adapter.jdbc;

import com.clouway.trigger.core.Provider;
import com.clouway.trigger.core.Vendor;
import com.clouway.trigger.core.VendorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentVendorRepository implements VendorRepository {
  private Provider<Connection> provider;

  public PersistentVendorRepository(Provider provider) {
    this.provider = provider;
  }

  @Override
  public void register(Vendor vendor) {
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("INSERT INTO vendor values(?,?,?,?)")) {
      preparedStatement.setInt(1, vendor.id);
      preparedStatement.setString(2, vendor.firstName);
      preparedStatement.setString(3, vendor.lastName);
      preparedStatement.setInt(4, vendor.age);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Vendor findById(int id) {
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("SELECT * FROM vendor WHERE idVendor=?")) {
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
  public void updateById(int id, Vendor vendor) {
    try (PreparedStatement preparedStatement = provider.provide().prepareStatement("UPDATE vendor SET firstName=?,lastName=?,age=? WHERE idVendor=?")) {

      preparedStatement.setString(1, vendor.firstName);
      preparedStatement.setString(2, vendor.lastName);
      preparedStatement.setInt(3, vendor.age);
      preparedStatement.setInt(4, id);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

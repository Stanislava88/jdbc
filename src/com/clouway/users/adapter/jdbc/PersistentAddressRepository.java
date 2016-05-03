package com.clouway.users.adapter.jdbc;

import com.clouway.users.core.Address;
import com.clouway.users.core.AddressRepository;
import com.clouway.users.core.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentAddressRepository implements AddressRepository {
  private Provider<Connection> provider;

  public PersistentAddressRepository(Provider provider) {
    this.provider = provider;
  }

  @Override
  public void register(Address address) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO address(id,city,street,number)VALUES (?,?,?,?)")) {

      preparedStatement.setInt(1, address.id);
      preparedStatement.setString(2, address.city);
      preparedStatement.setString(3, address.street);
      preparedStatement.setInt(4, address.number);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }

  @Override
  public Address findById(int id) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM address WHERE id=?")) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");

        return new Address(id, city, street, number);
      }
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public List<Address> findAll() {
    List<Address> addresses = new ArrayList<>();
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM address")) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        int id = resultSet.getInt("id");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");

        addresses.add(new Address(id, city, street, number));
      }
    } catch (SQLException e) {
    }
    return addresses;
  }
}


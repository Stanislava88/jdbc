package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.Provider;
import com.clouway.tripagency.core.TripsPeopleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTripsPeopleRepository implements TripsPeopleRepository {
  private Provider<Connection> provider;

  public PersistentTripsPeopleRepository(Provider provider) {
    this.provider = provider;
  }

  @Override
  public List<Person> overlapPeopleInCity(long startDate, long endDate, String city) {
    List<Person> people = new ArrayList<>();
    String subQuery = "SELECT people.*, trip.datearrived, trip.datedeparture, trip.city FROM trip,people " +
            "WHERE trip.egn=people.egn " +
            "AND  datearrived<=" + endDate +
            "and datedeparture>'" + startDate +
            "' and city ='" + city + "'";

    String query = "SELECT * FROM (" + subQuery + ")" +
            "AS result1 inner join (" + subQuery + ") " +
            "AS result2 on result1.city=result2.city " +
            "WHERE result1.egn!=result2.egn and result1.datearrived<=result2.datedeparture " +
            "AND result1.datedeparture>=result2.datearrived";

    try (PreparedStatement preparedStatement = provider.get().prepareStatement(query)) {
      ResultSet resultSet = preparedStatement.executeQuery();

      getPeople(people, resultSet);

      return people;
    } catch (SQLException e) {
    }
    return null;
  }

  private void getPeople(List<Person> persons, ResultSet resultSet) {
    try {
      while (resultSet.next()) {

        String name = resultSet.getString("name");
        String egn = resultSet.getString("egn");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");

        Person person = new Person(name, egn, age, email);

        if (!persons.contains(person)) {
          persons.add(new Person(name, egn, age, email));
        }
      }
    } catch (SQLException e) {
    }
  }
}


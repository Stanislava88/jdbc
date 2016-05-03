package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentCityRepository;
import com.clouway.tripagency.core.City;
import com.clouway.tripagency.core.CityRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.Provider;
import com.clouway.tripagency.core.Trip;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentCityRepositoryTest {
  private Provider<Connection> provider;
  private CityRepository repository;
  private Connection connection;


  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();
    repository = new PersistentCityRepository(provider);
    connection = provider.get();

    cleanUp();
  }

  @After
  public void tearDown() throws Exception {
    connection.close();
  }

  @Test
  public void findMostVisitedCities() throws Exception {
    City sofia = new City("Sofia");
    City varna = new City("Varna");
    Person person = new Person("Maria", "1111111111", 26, "maria@abv.bg");

    registerPeople(person);

    Trip trip1 = new Trip(1, person.egn, getDate("20-02-2012"), getDate("23-02-2012"), sofia.name);
    Trip trip2 = new Trip(2, person.egn, getDate("30-02-2012"), getDate("30-03-2012"), varna.name);
    Trip trip3 = new Trip(3, person.egn, getDate("20-04-2015"), getDate("30-06-2015"), sofia.name);
    Trip trip4 = new Trip(2, person.egn, getDate("30-02-2012"), getDate("30-03-2012"), varna.name);
    Trip trip5 = new Trip(2, person.egn, getDate("30-02-2012"), getDate("30-03-2012"), varna.name);

    registerTrip(trip1, trip2, trip3, trip4, trip5);

    List<City> actual = repository.findMostVisited();
    List<City> expected = Lists.newArrayList(varna,sofia);

    assertThat(actual, is(equalTo(expected)));
  }

  private long getDate(String dateAsString) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    Date dateForm = new Date(dateFormat.parse(dateAsString).getTime());

    return dateForm.getTime();
  }

  private void registerPeople(Person... people) throws ExecutionException {
    try {
      PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO people(name,egn,age,email) VALUES (?,?,?,?)");

      for (Person person : people) {

        preparedStatement.setString(1, person.name);
        preparedStatement.setString(2, person.egn);
        preparedStatement.setInt(3, person.age);
        preparedStatement.setString(4, person.email);

        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void registerTrip(Trip... trips) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO trip(id,egn,dateArrived,dateDeparture,city) VALUES (?,?,?,?,?)")) {
      for (Trip trip : trips) {
        preparedStatement.setInt(1, trip.id);
        preparedStatement.setString(2, trip.egn);
        preparedStatement.setLong(3, new Date(trip.dateArrived).getTime());
        preparedStatement.setLong(4, new Date(trip.dateDeparture).getTime());
        preparedStatement.setString(5, trip.city);

        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
    }
  }

  private void cleanUp() {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("TRUNCATE trip,people")) {

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}


package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentTripRepository;
import com.clouway.tripagency.core.City;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.Provider;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTripRepositoryTest {
  private Provider<Connection> provider;
  private TripRepository repository;
  private Connection connection;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();
    repository = new PersistentTripRepository(provider);
    connection = provider.get();

    cleanUp();
  }

  @After
  public void tearDown() throws Exception {
    connection.close();
  }

  @Test
  public void happyPath() throws Exception {
    City city = new City("Sofia");

    Person person = new Person("Ivan", "0000000000", 23, "ivan@abv.bg");
    pretendedPeopleAre(person);

    long dateArrived = getDate("20-02-2012");
    long dateDeparture = getDate("23-02-2012");

    Trip trip = new Trip(1, person.egn, dateArrived, dateDeparture, city.name);
    repository.register(trip);

    Trip actual = repository.findById(1);

    assertThat(actual, is(trip));
  }

  @Test
  public void findUnregisteredTrip() throws Exception {
    Trip actual = repository.findById(2);

    assertThat(actual, is(equalTo(null)));
  }

  @Test
  public void update() throws Exception {
    Person person = new Person("Maria", "1111111111", 26, "maria@abv.bg");
    pretendedPeopleAre(person);

    Trip trip = new Trip(1, person.egn, getDate("20-03-2015"), getDate("22-03-2015"), "Sofia");
    Trip updatedTrip = new Trip(1, person.egn, trip.dateArrived, trip.dateDeparture, "Varna");

    repository.register(trip);
    repository.updateById(1, updatedTrip);

    Trip actual = repository.findById(1);

    assertThat(actual, is(updatedTrip));
  }

  private long getDate(String dateAsString) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    Date dateForm = new Date(dateFormat.parse(dateAsString).getTime());

    return dateForm.getTime();
  }

  private void cleanUp() throws ExecutionException {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("TRUNCATE TABLE trip,people")) {

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void pretendedPeopleAre(Person... people) throws ExecutionException {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO people(name,egn,age,email) VALUES (?,?,?,?)")) {

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
}

package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentTripsPeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.Provider;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripsPeopleRepository;
import com.google.common.collect.Lists;
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

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTripsPeopleRepositoryTest {
  private Provider<Connection> provider;
  private TripsPeopleRepository repository;

  @Before
  public void setUp() throws Exception {
    provider = new ConnectionProvider();
    repository = new PersistentTripsPeopleRepository(provider);

    PreparedStatement preparedStatement = provider.get().prepareStatement("TRUNCATE TABLE trip,people");
    preparedStatement.executeUpdate();
  }

  @Test
  public void overlapPeopleInCity() throws Exception {
    Person person1 = new Person("Ivan", "0000000000", 23, "ivan@abv.bg");
    Person person2 = new Person("Maria", "1111111111", 26, "maria@abv.bg");
    Person person3 = new Person("Krasimir", "1231111111", 26, "maria@abv.bg");

    registerPeople(person1, person2, person3);

    Trip trip1 = new Trip(1, person1.egn, getDate("20-02-2012"), getDate("23-02-2012"), "Sofia");
    Trip trip2 = new Trip(2, person2.egn, getDate("22-02-2012"), getDate("30-02-2012"), "Sofia");
    Trip trip3 = new Trip(3, person3.egn, getDate("28-02-2012"), getDate("29-02-2012"), "Varna");

    registerTrips(trip1, trip2, trip3);

    List<Person> actual = repository.overlapPeopleInCity(getDate("19-02-2012"), getDate("25-02-2012"), "Sofia");
    List<Person> expected = Lists.newArrayList(person1, person2);

    assertThat(actual, is(expected));
  }

  @Test
  public void noOverlapPeopleInCity() throws Exception {
    Person person1 = new Person("Ivan", "0000000000", 23, "ivan@abv.bg");
    Person person2 = new Person("Maria", "1111111111", 26, "maria@abv.bg");
    Person person3 = new Person("Krasimir", "1231111111", 26, "maria@abv.bg");

    registerPeople(person1, person2, person3);

    Trip trip1 = new Trip(1, person1.egn, getDate("20-02-2012"), getDate("21-02-2012"), "Sofia");
    Trip trip2 = new Trip(2, person2.egn, getDate("23-02-2012"), getDate("30-02-2012"), "Sofia");
    Trip trip3 = new Trip(3, person3.egn, getDate("28-02-2012"), getDate("29-02-2012"), "Varna");

    registerTrips(trip1, trip2, trip3);

    List<Person> actual = repository.overlapPeopleInCity(getDate("19-02-2012"), getDate("25-02-2012"), "Sofia");
    List<Person> expected = Lists.newArrayList();

    assertThat(actual, is(expected));
  }

  private long getDate(String dateAsString) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    Date dateForm = new Date(dateFormat.parse(dateAsString).getTime());

    return dateForm.getTime();
  }

  private void registerPeople(Person... people) throws ExecutionException {
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

  private void registerTrips(Trip... trips) {
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
}

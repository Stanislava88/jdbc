package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.PersistentTripRepository;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripRepository;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistantTripRepositoryTest {
  private Connection connection;
  private TripRepository repository;

  @Before
  public void setUp() throws Exception {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost/tripagency", "postgres", "clouway.com");
    repository = new PersistentTripRepository(connection);
  }

  @Test
  public void happyPath() throws Exception {
    long dateArrived = getDate("20-02-2012");
    long dateDeparture = getDate("23-02-2012");
    Trip trip = new Trip("0000000000", dateArrived, dateDeparture, "Sofia");

    repository.register(trip);

    Trip expected = repository.findByEgn("0000000000");

    assertThat(expected, is(trip));
  }

  private long getDate(String dateAsString) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    Date dateForm = new Date(dateFormat.parse(dateAsString).getTime());

    return dateForm.getTime();
  }
}

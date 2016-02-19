package com.clouway.jdbc.task2;

import com.clouway.task2.domain.Person;
import com.clouway.task2.adapter.PersistentTripRepository;
import com.clouway.task2.domain.Trip;
import com.clouway.task2.domain.TripRequest;
import com.clouway.jdbc.task2.util.DatabaseCleaner;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.clouway.jdbc.task2.util.CalendarUtil.january;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentTripRepositoryTest {
    private MysqlConnectionPoolDataSource dataSourceTrip = new MysqlConnectionPoolDataSource();
    private MysqlConnectionPoolDataSource dataSourcePeople = new MysqlConnectionPoolDataSource();

    @Before
    public void setUp() {
        dataSourceTrip.setURL("jdbc:mysql://localhost:3306/task2");
        dataSourceTrip.setUser("root");
        dataSourceTrip.setPassword("clouway.com");

        dataSourcePeople.setURL("jdbc:mysql://localhost:3306/task2");
        dataSourcePeople.setUser("root");
        dataSourcePeople.setPassword("clouway.com");

        new DatabaseCleaner(dataSourcePeople, "people").cleanUp();
        new DatabaseCleaner(dataSourceTrip, "trip").cleanUp();
    }

    @Test
    public void oneTripRegisteredInRepository() {
        java.util.Date dateFrom=january(2010, 11);
        java.util.Date dateTo =january(2010, 18);
        Trip trip=new Trip("sliven", dateFrom, dateTo);
        TripRequest tripRequest = new TripRequest(new Person(123456789, "ivan", 23, "dasas@abv.bg"), trip);
        PersistentTripRepository persistentTripRepository = new PersistentTripRepository(dataSourceTrip);
        persistentTripRepository.register(tripRequest);
        assertThat(numberOfTrips(), is(equalTo(1)));
        assertDate(getTripFromDatabase().dateFrom, dateFrom);
        assertDate(getTripFromDatabase().dateTo, dateTo);
    }

    @Test
    public void peopleInCity() {
        TripRequest tripRequest1 = new TripRequest(new Person(123456789, "ivan", 23, "dasas@abv.bg"), new Trip("sliven", january(2010, 11), january(2010, 18)));
        TripRequest tripRequest2 = new TripRequest(new Person(345676321, "simeon", 24, "simo@abv.bg"), new Trip("vraca", january(2010, 3), january(2010, 12)));
        TripRequest tripRequest3 = new TripRequest(new Person(345676321, "simeon", 24, "simo@abv.bg"), new Trip("stz", january(2010, 4), january(2010, 18)));
        TripRequest tripRequest4 = new TripRequest(new Person(111111111, "kristiqn", 23, "kris@abv.bg"), new Trip("sliven", january(2010, 16), january(2010, 19)));
        TripRequest tripRequest5 = new TripRequest(new Person(987654321, "ivo", 23, "ivo@abv.bg"), new Trip("sliven", january(2010, 14), january(2010, 15)));

        PersistentTripRepository persistentTripRepository = new PersistentTripRepository(dataSourceTrip);
        persistentTripRepository.register(tripRequest1);
        persistentTripRepository.register(tripRequest2);
        persistentTripRepository.register(tripRequest3);
        persistentTripRepository.register(tripRequest4);
        persistentTripRepository.register(tripRequest5);

        List<Person> personList = persistentTripRepository.getPeopleInCityOnDate("sliven", january(2010, 13),january(2010, 17));

        assertThat(personList.size(), is(equalTo(3)));
    }

    @Test
    public void mostVisitedCities() {
        TripRequest tripRequest1 = new TripRequest(new Person(123456789, "ivan", 23, "dasas@abv.bg"), new Trip("sliven", january(2010, 11), january(2010, 18)));
        TripRequest tripRequest2 = new TripRequest(new Person(345676321, "simeon", 24, "simo@abv.bg"), new Trip("vraca", january(2010, 3), january(2010, 12)));
        TripRequest tripRequest3 = new TripRequest(new Person(345676321, "simeon", 24, "simo@abv.bg"), new Trip("stz", january(2010, 4), january(2010, 18)));
        TripRequest tripRequest4 = new TripRequest(new Person(111111111, "kristiqn", 23, "kris@abv.bg"), new Trip("sliven", january(2010, 16), january(2010, 19)));

        PersistentTripRepository persistentTripRepository = new PersistentTripRepository(dataSourceTrip);
        persistentTripRepository.register(tripRequest1);
        persistentTripRepository.register(tripRequest2);
        persistentTripRepository.register(tripRequest3);
        persistentTripRepository.register(tripRequest4);


        List<String> mostVisitedCities = persistentTripRepository.mostVisitedCities();

        assertThat(mostVisitedCities.get(0), is("sliven"));
    }

    @Test
    public void deleteContentOfTable() {
        TripRequest tripRequest1 = new TripRequest(new Person(123456789, "ivan", 23, "dasas@abv.bg"), new Trip("sliven", january(2010, 11), january(2010, 18)));
        TripRequest tripRequest2 = new TripRequest(new Person(345676321, "simeon", 24, "simo@abv.bg"), new Trip("vraca", january(2010, 3), january(2010, 12)));
        TripRequest tripRequest3 = new TripRequest(new Person(345676321, "simeon", 24, "simo@abv.bg"), new Trip("stz", january(2010, 4), january(2010, 18)));
        TripRequest tripRequest4 = new TripRequest(new Person(111111111, "kristiqn", 23, "kris@abv.bg"), new Trip("sliven", january(2010, 16), january(2010, 19)));

        PersistentTripRepository persistentTripRepository = new PersistentTripRepository(dataSourceTrip);
        persistentTripRepository.register(tripRequest1);
        persistentTripRepository.register(tripRequest2);
        persistentTripRepository.register(tripRequest3);
        persistentTripRepository.register(tripRequest4);

        assertThat(numberOfTrips(), is(equalTo(4)));

        persistentTripRepository.deleteTrips();

        assertThat(numberOfTrips(), is(equalTo(0)));
    }

    @After
    public void cleanUp() {
        new DatabaseCleaner(dataSourceTrip, "trip").cleanUp();
        new DatabaseCleaner(dataSourcePeople, "people").cleanUp();
    }

    private int numberOfTrips() {
        int size = 0;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSourceTrip.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from trip");
            while (rs.next()) {
                size++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    private void assertDate(java.util.Date dateFromDatabase, java.util.Date dateBeforeDatabase) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String fromDatabase=format.format(dateFromDatabase);
        String beforeDatabase=format.format(dateBeforeDatabase);
        assertThat(beforeDatabase,is(equalTo(fromDatabase)));
    }

    private Trip getTripFromDatabase(){
        Trip trip=null;
        Connection connection = null;
        Statement statement=null;
        try {
            connection = dataSourceTrip.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select city,date_arrive,departure_date from trip");
            while (rs.next()) {
                String city = rs.getString("city");
                Date date_arrive=rs.getDate("date_arrive");
                Date departure_date=rs.getDate("departure_date");
                trip=new Trip(city,date_arrive,departure_date);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return trip;
    }

}

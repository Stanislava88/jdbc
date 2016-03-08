package com.clouway.jdbc.travel.agency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class TravelAgencyTest {
    TravelAgency travelAgency = null;
    Calendar calendar = null;


    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection("travel_agency", "postgres", "clouway.com");
        travelAgency = new TravelAgency(connection);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
    }

    @After
    public void tearDown() throws Exception {
        travelAgency.clearClientRepository();
        travelAgency.clearTripRepository();
    }

    @Test
    public void addClient() throws SQLException {
        Person john = new Person("John", "1", 25, "john@johm.com");
        travelAgency.registerClient(john);
        Person johnReturned = travelAgency.getClient("1");
        assertThat(johnReturned, is(equalTo(john)));
    }

    @Test
    public void updateClientInfo() throws SQLException {
        Person mark = new Person("Mark", "123", 20, "mark@a.d");
        travelAgency.registerClient(mark);

        String newEmail = "mark@gmail.com";
        Person markUpdated = new Person(mark.name, mark.egn, 21, newEmail);
        travelAgency.updateClient(markUpdated);

        Person markUpdatedReturned = travelAgency.getClient("123");
        assertThat(markUpdatedReturned.email, is(equalTo(newEmail)));
    }

    @Test
    public void addTrip() throws SQLException {
        Person micky = new Person("Micky", "2", 31, "asfd@a.erg");
        travelAgency.registerClient(micky);

        calendar.set(2015, Calendar.MARCH, 5);
        Date arrival = new Date(calendar.getTimeInMillis());

        calendar.set(2015, Calendar.MARCH, 15);
        Date departure = new Date(calendar.getTimeInMillis());

        Trip toRom = new Trip(1, "2", arrival, departure, "Rom");
        travelAgency.bookTrip(toRom);

        Trip romBooked = travelAgency.getTrip(1);
        assertThat(romBooked, is(equalTo(toRom)));
    }

    @Test
    public void updateTripInfo() throws SQLException {
        Person bob = new Person("Bob", "5443", 15, "q@a.we");
        travelAgency.registerClient(bob);

        calendar.set(2015, Calendar.OCTOBER, 20);
        Date arrival = new Date(calendar.getTimeInMillis());

        calendar.set(2015, Calendar.NOVEMBER, 1);
        Date departure = new Date(calendar.getTimeInMillis());

        Trip toRom = new Trip(1, "5443", arrival, departure, "Rom");
        travelAgency.bookTrip(toRom);

        calendar.set(Calendar.DAY_OF_MONTH, 19);
        arrival.setTime(calendar.getTimeInMillis());

        Trip toRomUpdated = new Trip(toRom.id, toRom.egn, arrival, toRom.departure, toRom.destination);
        travelAgency.updateTrip(toRomUpdated);

        Trip romUpdatedReturned = travelAgency.getTrip(1);
        assertThat(romUpdatedReturned.arrival, is(equalTo(arrival)));
    }

    @Test
    public void clientList() throws SQLException {
        Person linch = new Person("Linch", "1", 20, "linch@a.bg");
        Person pako = new Person("Pako", "2", 27, "pako@a.bg");
        Person zoro = new Person("Zoro", "3", 50, "zoro@zoro.zoro");

        List<Person> expectedClients = new ArrayList<>();
        expectedClients.add(linch);
        expectedClients.add(pako);
        expectedClients.add(zoro);

        travelAgency.registerClient(linch);
        travelAgency.registerClient(pako);
        travelAgency.registerClient(zoro);

        assertThat(travelAgency.getClientList(), is(equalTo(expectedClients)));
    }

    @Test
    public void getTripList() throws SQLException {
        Person malcho = new Person("Malcho", "1", 32, "malcho@a.a");
        Person mirela = new Person("Mirela", "2", 20, "mirela@m.m");
        travelAgency.registerClient(malcho);
        travelAgency.registerClient(mirela);

        calendar.set(2012, Calendar.JANUARY, 12);
        Date vegasArrival = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        Trip toVegas = new Trip(1, "1", vegasArrival, new Date(calendar.getTimeInMillis()), "Las Vegas");

        calendar.set(2014, Calendar.NOVEMBER, 21);
        Date parisArrival = new Date(calendar.getTimeInMillis());
        calendar.set(2012, Calendar.NOVEMBER, 25);
        Trip toParis = new Trip(2, "2", parisArrival, new Date(calendar.getTimeInMillis()), "Paris");

        List<Trip> trips = new ArrayList<>();
        trips.add(toVegas);
        trips.add(toParis);

        travelAgency.bookTrip(toVegas);
        travelAgency.bookTrip(toParis);

        assertThat(travelAgency.getTripsList(), is(equalTo(trips)));
    }

    @Test
    public void clientListNamesBeginning() throws SQLException {
        Person kaloian = new Person("Kaloian", "1", 12, "kalata@k.k");
        Person krasimir = new Person("Krasimir", "2", 22, "k@k.k");
        Person petar = new Person("Petar", "3", 40, "p@k.k");

        List<Person> peopleWithK = new ArrayList<>();
        peopleWithK.add(kaloian);
        peopleWithK.add(krasimir);

        travelAgency.registerClient(kaloian);
        travelAgency.registerClient(krasimir);
        travelAgency.registerClient(petar);

        assertThat(travelAgency.getClientsWith("K"), is(equalTo(peopleWithK)));
    }

    @Test
    public void peopleListTripOverlapOnRange() throws SQLException {
        Person rosen = new Person("Rosen", "1", 50, "rosen@r.bg");
        Person bilqn = new Person("Bilqn", "2", 30, "bilqn@b.bg");
        Person mihail = new Person("Mihail", "3", 35, "mishkata@m.bg");

        travelAgency.registerClient(rosen);
        travelAgency.registerClient(bilqn);
        travelAgency.registerClient(mihail);

        Trip rosenRom = new Trip(1, "1", Date.valueOf("2016-7-3"), Date.valueOf("2016-7-9"), "Rom");
        Trip bilqnRom = new Trip(2, "2", Date.valueOf("2016-7-7"), Date.valueOf("2016-7-15"), "Rom");
        Trip mihailRom = new Trip(3, "3", Date.valueOf("2016-7-16"), Date.valueOf("2016-7-22"), "Rom");

        travelAgency.bookTrip(rosenRom);
        travelAgency.bookTrip(bilqnRom);
        travelAgency.bookTrip(mihailRom);

        List<Person> expected = new ArrayList<>();
        expected.add(rosen);
        expected.add(bilqn);


        assertThat(travelAgency.tripsOverlapBetween(Date.valueOf("2016-7-4"), Date.valueOf("2016-7-20"), "Rom"), is(equalTo(expected)));
    }

    @Test
    public void anotherCityExcluded() throws SQLException {
        Person rosen = new Person("Rosen", "1", 50, "rosen@r.bg");
        Person bilqn = new Person("Bilqn", "2", 30, "bilqn@b.bg");
        Person mihail = new Person("Mihail", "3", 35, "mishkata@m.bg");
        Person ivan = new Person("Ivan", "4", 21, "ivan@asdf.dfv");

        travelAgency.registerClient(rosen);
        travelAgency.registerClient(bilqn);
        travelAgency.registerClient(mihail);
        travelAgency.registerClient(ivan);

        Trip rosenRom = new Trip(1, "1", Date.valueOf("2016-7-3"), Date.valueOf("2016-7-9"), "Rom");
        Trip bilqnRom = new Trip(2, "2", Date.valueOf("2016-7-7"), Date.valueOf("2016-7-15"), "Rom");
        Trip mihailRom = new Trip(3, "3", Date.valueOf("2016-7-16"),Date.valueOf("2016-7-22"), "Rom");
        Trip ivanParis = new Trip(4, "4", Date.valueOf("2016-7-5"), Date.valueOf("2016-7-10"), "Paris");

        travelAgency.bookTrip(rosenRom);
        travelAgency.bookTrip(bilqnRom);
        travelAgency.bookTrip(mihailRom);
        travelAgency.bookTrip(ivanParis);


        List<Person> expected = new ArrayList<>();
        expected.add(rosen);
        expected.add(bilqn);


        assertThat(travelAgency.tripsOverlapBetween(Date.valueOf("2016-7-4"), Date.valueOf("2016-7-20"), "Rom"), is(equalTo(expected)));
    }

    @Test
    public void sortCitiesByVisitation() throws SQLException {
        Person kiko = new Person("Kristian", "1", 23, "kiko@k.k");
        Person kiro = new Person("Kircho", "2", 43, "as@as.as");
        Person petko = new Person("Petko", "3", 27, "pepo@p.p");

        travelAgency.registerClient(kiko);
        travelAgency.registerClient(kiro);
        travelAgency.registerClient(petko);

        Trip kikoSofia = new Trip(1, "1", Date.valueOf("2016-7-3"), Date.valueOf("2016-7-9"), "Sofia");
        Trip kiroSofia = new Trip(2, "2", Date.valueOf("2016-7-7"), Date.valueOf("2016-7-15"), "Sofia");

        Trip petkoPlovdiv = new Trip(3, "3", Date.valueOf("2016-7-16"), Date.valueOf("2016-7-22"), "Plovdiv");

        travelAgency.bookTrip(kikoSofia);
        travelAgency.bookTrip(kiroSofia);
        travelAgency.bookTrip(petkoPlovdiv);

        List<String> cities = new ArrayList<>();
        cities.add("Sofia");
        cities.add("Plovdiv");


        assertThat(travelAgency.citiesByPopularity(), is(equalTo(cities)));
    }

    @Test
    public void sortCitiesByNameWhenSameVisitation() throws SQLException {
        Person kaloian = new Person("Kaloian", "1", 12, "as@sd.d");
        Person george = new Person("George", "2", 34, "asd@w.d");
        Person mirela = new Person("Mirela", "3", 65, "wer.t");

        travelAgency.registerClient(kaloian);
        travelAgency.registerClient(george);
        travelAgency.registerClient(mirela);

        Trip kaloianVarna = new Trip(1, "1", Date.valueOf("2015-5-15"), Date.valueOf("2015-6-15"), "Varna");
        Trip georgeVarna = new Trip(2, "2", Date.valueOf("2015-4-12"), Date.valueOf("2015-4-20"), "Varna");
        Trip mirelaBurgas = new Trip(3, "3", Date.valueOf("2015-4-12"), Date.valueOf("2015-4-20"), "Burgas");
        Trip mirelaBurgasAgain = new Trip(4, "3", Date.valueOf("2015-4-12"), Date.valueOf("2015-4-20"), "Burgas");

        travelAgency.bookTrip(kaloianVarna);
        travelAgency.bookTrip(georgeVarna);
        travelAgency.bookTrip(mirelaBurgas);
        travelAgency.bookTrip(mirelaBurgasAgain);

        List<String> cities = new ArrayList<>();
        cities.add("Burgas");
        cities.add("Varna");

        assertThat(travelAgency.citiesByPopularity(), is(equalTo(cities)));
    }

    @Test
    public void truncateClientTable() throws SQLException {
        Person ralica = new Person("Ralica", "1", 84, "rali@gmail.com");
        travelAgency.registerClient(ralica);

        travelAgency.clearClientRepository();

        Person ralicaBack = travelAgency.getClient("1");
        assertThat(ralicaBack, is(equalTo(null)));
    }

    @Test
    public void truncateTripTable() throws SQLException {
        Person ivon = new Person("Ivon", "1", 23, "rali@gmail.com");
        travelAgency.registerClient(ivon);

        Trip ivonBurgas = new Trip(1, "1", Date.valueOf("2016-8-12"), Date.valueOf("2016-8-20"), "Burgas");
        travelAgency.bookTrip(ivonBurgas);

        travelAgency.clearTripRepository();

        Trip ivonBurgasBack = travelAgency.getTrip(1);
        assertThat(ivonBurgasBack, is(equalTo(null)));
    }

    @Test
    public void tableExist() throws SQLException {
        boolean tableExist = travelAgency.tableExist("trip");

        assertThat(tableExist, is(equalTo(true)));
    }

    @Test
    public void tableDoesntExist() throws SQLException {
        boolean tableDoesntExist = travelAgency.tableExist("woble");

        assertThat(tableDoesntExist, is(equalTo(false)));
    }

    @Test
    public void destroyClientTable() throws SQLException {
        travelAgency.destroyTable("people");

        boolean tableDestroyed = !travelAgency.tableExist("people");

        travelAgency.createClientRepository();
        assertThat(tableDestroyed, is(equalTo(true)));
    }

    @Test
    public void destroyTripTable() throws SQLException {
        travelAgency.destroyTable("trip");

        boolean tableDestroyed = !travelAgency.tableExist("trip");

        travelAgency.createTripRepository();
        assertThat(tableDestroyed, is(equalTo(true)));
    }
}

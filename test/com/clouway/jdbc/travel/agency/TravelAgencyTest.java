package com.clouway.jdbc.travel.agency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class TravelAgencyTest {
    TravelAgency travelAgency = null;


    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection("travel_agency", "postgres", "clouway.com");
        travelAgency = new TravelAgency(connection);
    }

    @After
    public void tearDown() throws Exception {
        travelAgency.clearClientRepository();
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
        Person markUpdated = new Person("Mark", "123", 21, newEmail);
        travelAgency.updateClient(markUpdated);

        Person markUpdatedReturned = travelAgency.getClient("123");
        assertThat(markUpdatedReturned.getEmail(), is(equalTo(newEmail)));
    }

    @Test
    public void addTrip() throws SQLException {
        Person micky = new Person("Micky", "2", 31, "asfd@a.erg");
        travelAgency.registerClient(micky);

        Date arrival = new Date(115, 3, 10);
        Date departure = new Date(115, 3, 15);
        Trip toRom = new Trip(1, "2", arrival, departure, "Rom");
        travelAgency.bookTrip(toRom);

        Trip romBooked = travelAgency.getTrip(1);
        assertThat(romBooked, is(equalTo(toRom)));
    }

    @Test
    public void updateTripInfo() throws SQLException {
        Person bob = new Person("Bob", "5443", 15, "q@a.we");
        travelAgency.registerClient(bob);

        Date arrival = new Date(115, 10, 20);
        Date departure = new Date(115, 11, 1);
        Trip toRom = new Trip(1, "5443", arrival, departure, "Rom");
        travelAgency.bookTrip(toRom);

        arrival.setDate(19);
        Trip toRomUpdated = new Trip(1, "5443", arrival, departure, "Rom");
        travelAgency.updateTrip(toRomUpdated);

        Trip romUpdatedReturned = travelAgency.getTrip(1);
        assertThat(romUpdatedReturned.getArrival(), is(equalTo(arrival)));
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

        Trip toVegas = new Trip(1, "1", new Date(112, 01, 12), new Date(112, 02, 01), "Las Vegas");
        Trip toParis = new Trip(2, "2", new Date(114, 11, 21), new Date(112, 11, 25), "Paris");

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

        Trip rosenRom = new Trip(1, "1", new Date(116, 6, 3), new Date(116, 6, 9), "Rom");
        Trip bilqnRom = new Trip(2, "2", new Date(116, 6, 7), new Date(116, 6, 15), "Rom");
        Trip mihailRom = new Trip(3, "3", new Date(116, 6, 16), new Date(116, 6, 22), "Rom");

        travelAgency.bookTrip(rosenRom);
        travelAgency.bookTrip(bilqnRom);
        travelAgency.bookTrip(mihailRom);


        List<Person> expected =new ArrayList<>();
        expected.add(rosen);
        expected.add(bilqn);



        assertThat(travelAgency.tripsOverlapBetween(new Date(116, 6, 4), new Date(116, 6, 20)), is(equalTo(expected)));
    }

    @Test
    public void sortCitiesByVisitation() throws SQLException {
        Person kiko = new Person("Kristian", "1", 23, "kiko@k.k");
        Person kiro = new Person("Kircho", "2", 43, "as@as.as");
        Person petko = new Person("Petko", "3", 27, "pepo@p.p");

        travelAgency.registerClient(kiko);
        travelAgency.registerClient(kiro);
        travelAgency.registerClient(petko);

        Trip kikoSofia = new Trip(1, "1", new Date(116, 6, 3), new Date(116, 6, 9), "Sofia");
        Trip kiroSofia = new Trip(2, "2", new Date(116, 6, 7), new Date(116, 6, 15), "Sofia");
        Trip petkoPlovdiv = new Trip(3, "3", new Date(116, 6, 16), new Date(116, 6, 22), "Plovdiv");

        travelAgency.bookTrip(kikoSofia);
        travelAgency.bookTrip(kiroSofia);
        travelAgency.bookTrip(petkoPlovdiv);

        List<String> cities = new ArrayList<>();
        cities.add("Sofia");
        cities.add("Plovdiv");

        assertThat(travelAgency.citiesByPopularity(), is(equalTo(cities)));
    }



}

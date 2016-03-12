package com.clouway.jdbc.travel.agency;

import com.clouway.jdbc.DatabaseTableTool;
import com.clouway.jdbc.travel.agency.persistence.*;
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
    Connection connection = null;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("travel_agency", "postgres", "clouway.com");
        travelAgency = new TravelAgency(new PersistentClientRepository(connection), new PersistentTripRepository(connection), new ClientTripsInfo(connection));
    }

    @After
    public void tearDown() throws SQLException {
        DatabaseTableTool tableTool = new DatabaseTableTool();
        tableTool.clearTable(connection, "people");
        tableTool.clearTable(connection, "trip");
        connection.close();
    }

    @Test
    public void addClient() {
        Client john = new Client("John", "1", 25, "john@johm.com");
        travelAgency.registerClient(john);
        Client johnReturned = travelAgency.getClient("1");
        assertThat(johnReturned, is(equalTo(john)));
    }

    @Test
    public void updateClientInfo() {
        Client mark = new Client("Mark", "123", 20, "mark@a.d");
        travelAgency.registerClient(mark);

        String newEmail = "mark@gmail.com";
        Client markUpdated = new Client(mark.name, mark.egn, 21, newEmail);
        travelAgency.updateClient(markUpdated);

        Client markUpdatedReturned = travelAgency.getClient("123");
        assertThat(markUpdatedReturned.email, is(equalTo(newEmail)));
    }

    @Test
    public void addTrip() {
        Client micky = new Client("Micky", "2", 31, "asfd@a.erg");
        travelAgency.registerClient(micky);

        Trip toRom = new Trip(1, "2", Date.valueOf("2015-3-5"), Date.valueOf("2015-3-15"), "Rom");
        travelAgency.bookTrip(toRom);

        Trip romBooked = travelAgency.getTrip(1);
        assertThat(romBooked, is(equalTo(toRom)));
    }

    @Test
    public void updateTripInfo() {
        Client bob = new Client("Bob", "5443", 15, "q@a.we");
        travelAgency.registerClient(bob);


        Trip toRom = new Trip(1, "5443", Date.valueOf("2015-10-20"), Date.valueOf("2015-11-1"), "Rom");
        travelAgency.bookTrip(toRom);

        Trip toRomUpdated = new Trip(toRom.id, toRom.egn, Date.valueOf("2015-10-19"), toRom.departure, toRom.destination);
        travelAgency.updateTrip(toRomUpdated);

        Trip romUpdatedReturned = travelAgency.getTrip(1);
        assertThat(romUpdatedReturned.arrival, is(equalTo(Date.valueOf("2015-10-19"))));
    }

    @Test
    public void clientList() {
        Client linch = new Client("Linch", "1", 20, "linch@a.bg");
        Client pako = new Client("Pako", "2", 27, "pako@a.bg");
        Client zoro = new Client("Zoro", "3", 50, "zoro@zoro.zoro");

        List<Client> expectedClients = new ArrayList<>();
        expectedClients.add(linch);
        expectedClients.add(pako);
        expectedClients.add(zoro);

        registerToAgency(linch, pako, zoro);

        assertThat(travelAgency.getClientsList(), is(equalTo(expectedClients)));
    }

    @Test
    public void getTripList() {
        Client malcho = new Client("Malcho", "1", 32, "malcho@a.a");
        Client mirela = new Client("Mirela", "2", 20, "mirela@m.m");
        registerToAgency(malcho, mirela);

        Trip toVegas = new Trip(1, "1", Date.valueOf("2012-1-12"), Date.valueOf("2012-1-22"), "Las Vegas");
        Trip toParis = new Trip(2, "2", Date.valueOf("2014-11-21"), Date.valueOf("2014-11-22"), "Paris");

        List<Trip> trips = new ArrayList<>();
        trips.add(toVegas);
        trips.add(toParis);

        bookToAgency(toVegas, toParis);

        assertThat(travelAgency.getTripsList(), is(equalTo(trips)));
    }

    @Test
    public void clientListNamesBeginning() {
        Client kaloian = new Client("Kaloian", "1", 12, "kalata@k.k");
        Client krasimir = new Client("Krasimir", "2", 22, "k@k.k");
        Client petar = new Client("Petar", "3", 40, "p@k.k");

        List<Client> peopleWithK = new ArrayList<>();
        peopleWithK.add(kaloian);
        peopleWithK.add(krasimir);

        registerToAgency(kaloian, krasimir, petar);

        assertThat(travelAgency.getClientsWith("K"), is(equalTo(peopleWithK)));
    }

    @Test
    public void peopleListTripOverlapOnRange() {
        Client rosen = new Client("Rosen", "1", 50, "rosen@r.bg");
        Client bilqn = new Client("Bilqn", "2", 30, "bilqn@b.bg");
        Client mihail = new Client("Mihail", "3", 35, "mishkata@m.bg");

        registerToAgency(rosen, bilqn, mihail);

        Trip rosenRom = new Trip(1, "1", Date.valueOf("2016-7-3"), Date.valueOf("2016-7-9"), "Rom");
        Trip bilqnRom = new Trip(2, "2", Date.valueOf("2016-7-7"), Date.valueOf("2016-7-15"), "Rom");
        Trip mihailRom = new Trip(3, "3", Date.valueOf("2016-7-16"), Date.valueOf("2016-7-22"), "Rom");

        bookToAgency(rosenRom, bilqnRom, mihailRom);

        List<Client> expected = new ArrayList<>();
        expected.add(rosen);
        expected.add(bilqn);


        assertThat(travelAgency.tripsOverlapBetween(Date.valueOf("2016-7-4"), Date.valueOf("2016-7-20"), "Rom"), is(equalTo(expected)));
    }

    @Test
    public void anotherCityExcluded() {
        Client rosen = new Client("Rosen", "1", 50, "rosen@r.bg");
        Client bilqn = new Client("Bilqn", "2", 30, "bilqn@b.bg");
        Client mihail = new Client("Mihail", "3", 35, "mishkata@m.bg");
        Client ivan = new Client("Ivan", "4", 21, "ivan@asdf.dfv");

        registerToAgency(rosen, bilqn, mihail, ivan);

        Trip rosenRom = new Trip(1, "1", Date.valueOf("2016-7-3"), Date.valueOf("2016-7-9"), "Rom");
        Trip bilqnRom = new Trip(2, "2", Date.valueOf("2016-7-7"), Date.valueOf("2016-7-15"), "Rom");
        Trip mihailRom = new Trip(3, "3", Date.valueOf("2016-7-16"), Date.valueOf("2016-7-22"), "Rom");
        Trip ivanParis = new Trip(4, "4", Date.valueOf("2016-7-5"), Date.valueOf("2016-7-10"), "Paris");

        bookToAgency(rosenRom, bilqnRom, mihailRom, ivanParis);


        List<Client> expected = new ArrayList<>();
        expected.add(rosen);
        expected.add(bilqn);


        assertThat(travelAgency.tripsOverlapBetween(Date.valueOf("2016-7-4"), Date.valueOf("2016-7-20"), "Rom"), is(equalTo(expected)));
    }

    @Test
    public void twoPeopleOverlap() {
        Client ivan = new Client("Ivan", "1", 21, "ivan@asd.com");
        Client petar = new Client("Petar", "2", 32, "petar@asd.com");
        Client petko = new Client("Petko", "3", 18, "petko@asd.com");
        registerToAgency(ivan, petar, petko);


        Trip sofia = new Trip(1, "1", Date.valueOf("2015-05-10"), Date.valueOf("2015-05-21"), "Sofia");
        Trip sofia1 = new Trip(2, "2", Date.valueOf("2015-05-10"), Date.valueOf("2015-05-21"), "Sofia");
        Trip plovdiv = new Trip(3, "3", Date.valueOf("2015-06-10"), Date.valueOf("2015-06-21"), "Plovdiv");
        bookToAgency(sofia, sofia1, plovdiv);

        List<Client> people = travelAgency.tripsOverlapBetween(Date.valueOf("2015-05-10"), Date.valueOf("2016-5-21"), "Sofia");

        assertThat(people.size(), is(2));
        assertThat(people.get(0).name, is("Ivan"));
        assertThat(people.get(1).name, is("Petar"));
    }

    @Test
    public void oneIsNotInRangeAndNotInCity() {
        registerToAgency(new Client("Ivan", "1", 21, "ivan@asd.com"),
                new Client("Petar", "2", 32, "petar@asd.com"),
                new Client("Petko", "3", 18, "petko@asd.com"));

        bookToAgency(new Trip(1, "1", Date.valueOf("2015-05-12"), Date.valueOf("2015-05-25"), "Sofia"),
                new Trip(2, "2", Date.valueOf("2015-05-10"), Date.valueOf("2015-05-21"), "Sofia"),
                new Trip(3, "3", Date.valueOf("2015-06-10"), Date.valueOf("2015-06-21"), "Plovdiv"));

        List<Client> people = travelAgency.tripsOverlapBetween(Date.valueOf("2015-05-1"), Date.valueOf("2016-5-30"), "Sofia");

        assertThat(people.size(), is(2));
        assertThat(people.get(0).name, is("Ivan"));
    }

    @Test
    public void threeOutOfSixInRangeAndCity() {
        registerToAgency(new Client("Ivan", "1", 21, "ivan@asd.com"),
                new Client("Petar", "2", 32, "petar@asd.com"),
                new Client("Petko", "3", 18, "petko@asd.com"),
                new Client("Dimitar", "4", 20, "asdqweqwe@asd.com"),
                new Client("Tihomir", "5", 27, "Tihomir@asd.com"),
                new Client("Panayot", "6", 34, "jiji@asd.com"));

        bookToAgency(new Trip(1, "1", Date.valueOf("2015-05-01"), Date.valueOf("2015-05-05"), "Sofia"),
                new Trip(2, "2", Date.valueOf("2015-05-10"), Date.valueOf("2015-05-21"), "Sofia"),
                new Trip(3, "3", Date.valueOf("2015-05-10"), Date.valueOf("2015-05-21"), "Plovdiv"),
                new Trip(4, "4", Date.valueOf("2015-05-15"), Date.valueOf("2015-05-28"), "Sofia"),
                new Trip(5, "5", Date.valueOf("2015-05-06"), Date.valueOf("2015-05-29"), "Sofia"),
                new Trip(6, "6", Date.valueOf("2015-05-31"), Date.valueOf("2015-06-10"), "Sofia"));

        List<Client> people = travelAgency.tripsOverlapBetween(Date.valueOf("2015-05-06"), Date.valueOf("2015-05-30"), "Sofia");
        assertThat(people.size(), is(3));
        assertThat(people.get(0).name, is("Petar"));
        assertThat(people.get(1).name, is("Dimitar"));
    }

    @Test
    public void twoInRange() {
        registerToAgency(new Client("Ivan", "1", 21, "ivan@asd.com"),
                new Client("Petar", "2", 32, "petar@asd.com"),
                new Client("Petko", "3", 18, "petko@asd.com"));

        bookToAgency(new Trip(1, "1", Date.valueOf("2015-05-01"), Date.valueOf("2015-05-06"), "Sofia"),
                new Trip(2, "2", Date.valueOf("2015-05-04"), Date.valueOf("2015-05-20"), "Sofia"),
                new Trip(3, "3", Date.valueOf("2015-05-12"), Date.valueOf("2015-05-28"), "Sofia"));


        List<Client> people = travelAgency.tripsOverlapBetween(Date.valueOf("2015-05-10"), Date.valueOf("2015-05-20"), "Sofia");
        assertThat(people.size(), is(2));
    }

    @Test
    public void oneInRaneButOutsideCityParameter() {
        registerToAgency(new Client("Ivan", "1", 21, "ivan@asd.com"),
                new Client("Petar", "2", 32, "petar@asd.com"), new Client("Petko", "3", 18, "petko@asd.com"));

        bookToAgency(new Trip(1, "1", Date.valueOf("2014-02-07"), Date.valueOf("2014-02-12"), "Burgas"),
                new Trip(2, "2", Date.valueOf("2014-02-08"), Date.valueOf("2014-02-14"), "Sofia"),
                new Trip(3, "3", Date.valueOf("2014-02-10"), Date.valueOf("2014-02-16"), "Burgas"));

        List<Client> people = travelAgency.tripsOverlapBetween(Date.valueOf("2014-02-10"), Date.valueOf("2014-02-20"), "Burgas");

        assertThat(people.size(), is(2));
    }

    @Test
    public void sortCitiesByVisitation() {
        Client kiko = new Client("Kristian", "1", 23, "kiko@k.k");
        Client kiro = new Client("Kircho", "2", 43, "as@as.as");
        Client petko = new Client("Petko", "3", 27, "pepo@p.p");

        registerToAgency(kiko, kiro, petko);

        Trip kikoSofia = new Trip(1, "1", Date.valueOf("2016-7-3"), Date.valueOf("2016-7-9"), "Sofia");
        Trip kiroSofia = new Trip(2, "2", Date.valueOf("2016-7-7"), Date.valueOf("2016-7-15"), "Sofia");

        Trip petkoPlovdiv = new Trip(3, "3", Date.valueOf("2016-7-16"), Date.valueOf("2016-7-22"), "Plovdiv");

        bookToAgency(kikoSofia, kiroSofia, petkoPlovdiv);

        List<String> cities = new ArrayList<>();
        cities.add("Sofia");
        cities.add("Plovdiv");


        assertThat(travelAgency.citiesByPopularity(), is(equalTo(cities)));
    }

    @Test
    public void sortCitiesByNameWhenSameVisitation() {
        Client kaloian = new Client("Kaloian", "1", 12, "as@sd.d");
        Client george = new Client("George", "2", 34, "asd@w.d");
        Client mirela = new Client("Mirela", "3", 65, "wer.t");

        registerToAgency(kaloian, george, mirela);

        Trip kaloianVarna = new Trip(1, "1", Date.valueOf("2015-5-15"), Date.valueOf("2015-6-15"), "Varna");
        Trip georgeVarna = new Trip(2, "2", Date.valueOf("2015-4-12"), Date.valueOf("2015-4-20"), "Varna");
        Trip mirelaBurgas = new Trip(3, "3", Date.valueOf("2015-4-12"), Date.valueOf("2015-4-20"), "Burgas");
        Trip mirelaBurgasAgain = new Trip(4, "3", Date.valueOf("2015-4-12"), Date.valueOf("2015-4-20"), "Burgas");

        bookToAgency(kaloianVarna, georgeVarna, mirelaBurgas, mirelaBurgasAgain);

        List<String> cities = new ArrayList<>();
        cities.add("Burgas");
        cities.add("Varna");

        assertThat(travelAgency.citiesByPopularity(), is(equalTo(cities)));
    }

    private void registerToAgency(Client... people) {
        for (Client client : people) {
            travelAgency.registerClient(client);
        }
    }

    private void bookToAgency(Trip... trips) {
        for (Trip trip : trips) {
            travelAgency.bookTrip(trip);
        }
    }

}

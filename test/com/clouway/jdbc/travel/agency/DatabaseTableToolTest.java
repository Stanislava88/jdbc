package com.clouway.jdbc.travel.agency;

import com.clouway.jdbc.travel.agency.persistence.PersistentClientRepository;
import com.clouway.jdbc.travel.agency.persistence.PersistentTripRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class DatabaseTableToolTest {

    DatabaseTableTool tableTool = null;
    TravelAgency travelAgency = null;
    Connection connection = null;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection("travel_agency", "postgres", "clouway.com");
        tableTool = new DatabaseTableTool(connection);
        travelAgency = new TravelAgency(new PersistentClientRepository(connection), new PersistentTripRepository(connection), null);
    }

    @After
    public void tearDown() throws Exception {
        tableTool.clearClientRepository();
        tableTool.clearTripRepository();
    }

    @Test
    public void tableExist() throws SQLException {
        boolean tableExist = tableTool.tableExist("trip");

        assertThat(tableExist, is(equalTo(true)));
    }

    @Test
    public void tableDoesntExist() throws SQLException {
        boolean tableDoesntExist = tableTool.tableExist("woble");

        assertThat(tableDoesntExist, is(equalTo(false)));
    }

    @Test
    public void destroyClientTable() throws SQLException {
        tableTool.destroyTable("people");

        boolean tableDestroyed = !tableTool.tableExist("people");

        tableTool.createRepository("people(name TEXT NOT NULL,egn TEXT PRIMARY KEY NOT NULL, age INT, email TEXT)");
        assertThat(tableDestroyed, is(equalTo(true)));
    }

    @Test
    public void destroyTripTable() throws SQLException {
        tableTool.destroyTable("trip");

        boolean tableDestroyed = !tableTool.tableExist("trip");

        tableTool.createRepository("trip(id SERIAL PRIMARY KEY NOT NULL , egn TEXT REFERENCES people(egn), arrival DATE NOT NULL, departure DATE NOT NULL, city TEXT)");
        assertThat(tableDestroyed, is(equalTo(true)));
    }

    @Test(expected = NoSuchElementException.class)
    public void truncateClientTable() throws SQLException {
        Client ralica = new Client("Ralica", "1", 84, "rali@gmail.com");
        travelAgency.registerClient(ralica);

        tableTool.clearClientRepository();

        Client ralicaBack = travelAgency.getClient("1");
    }

    @Test(expected = NoSuchElementException.class)
    public void truncateTripTable() throws SQLException {
        Client ivon = new Client("Ivon", "1", 23, "rali@gmail.com");
        travelAgency.registerClient(ivon);

        Trip ivonBurgas = new Trip(1, "1", Date.valueOf("2016-8-12"), Date.valueOf("2016-8-20"), "Burgas");
        travelAgency.bookTrip(ivonBurgas);

        tableTool.clearTripRepository();

        Trip ivonBurgasBack = travelAgency.getTrip(1);
    }
}

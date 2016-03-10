package com.clouway.jdbc.travel.agency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class ClientsTripInfo {

    private Connection connection;

    public ClientsTripInfo(Connection connection) {
        this.connection = connection;
    }

    public List<Client> tripsOverlapBetween(Date startDate, Date endDate, String city) throws SQLException {
        String subQuery = "Select people.*, trip.arrival, trip.departure, trip.city from trip inner" +
                " join people on trip.egn=people.egn where arrival<'" + endDate + "' and departure>'" + startDate + "' and city ='" + city + "'";
        String query = "select * from (" + subQuery + ") as a inner join (" + subQuery + ") as b on a.city= b.city where "
                + " a.egn!=b.egn and a.arrival<b.departure and a.departure>b.arrival;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Client> peopleTripsOverlap = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String egn = resultSet.getString("egn");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            Client client = new Client(name, egn, age, email);
            if (!peopleTripsOverlap.contains(client)) {
                peopleTripsOverlap.add(client);
            }
        }
        resultSet.close();
        statement.close();
        return peopleTripsOverlap;
    }
}

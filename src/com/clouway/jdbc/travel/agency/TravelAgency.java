package com.clouway.jdbc.travel.agency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class TravelAgency {
    private Connection connection;

    public TravelAgency(Connection connection) {
        this.connection = connection;
    }

    public void registerClient(Person person) throws SQLException {
        String insertSql = "INSERT INTO people values(?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getEgn());
        preparedStatement.setInt(3, person.getAge());
        preparedStatement.setString(4, person.getEmail());
        preparedStatement.execute();
    }

    public Person getClient(String egn) throws SQLException {
        String selectUser = "Select * from people where egn=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
        preparedStatement.setString(1, egn);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String name = resultSet.getString("name");
        String egnReturned = resultSet.getString("egn");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");
        return new Person(name, egnReturned, age, email);
    }

    public void clearClientRepository() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE people CASCADE ;");
    }

    public void bookTrip(Trip tripName) throws SQLException {
        String insertTrip = "INSERT INTO trip values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertTrip);
        preparedStatement.setInt(1, tripName.getId());
        preparedStatement.setString(2, tripName.getEgn());
        preparedStatement.setDate(3, tripName.getArrival());
        preparedStatement.setDate(4, tripName.getDeparture());
        preparedStatement.setString(5, tripName.getDestination());
        preparedStatement.execute();
    }

    public Trip getTrip(int tripID) throws SQLException {
        String selectTrip = "SELECT * FROM trip WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectTrip);
        preparedStatement.setInt(1, tripID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("id");
        String egn = resultSet.getString("egn");
        Date arrival = resultSet.getDate("arrival");
        Date departure = resultSet.getDate("departure");
        String destination = resultSet.getString("city");
        return new Trip(id, egn, arrival, departure, destination);
    }

    public void updateClient(Person person) throws SQLException {
        String updatePeople = "Update people set name=?, egn=?, age=?, email=? where egn=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(updatePeople);
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getEgn());
        preparedStatement.setInt(3, person.getAge());
        preparedStatement.setString(4, person.getEmail());
        preparedStatement.setString(5, person.getEgn());
        preparedStatement.execute();
    }

    public void updateTrip(Trip trip) throws SQLException {
        String updateTrip = "UPDATE trip SET egn=?, arrival=?, departure=?, city=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateTrip);
        preparedStatement.setString(1, trip.getEgn());
        preparedStatement.setDate(2, trip.getArrival());
        preparedStatement.setDate(3, trip.getDeparture());
        preparedStatement.setString(4, trip.getDestination());
        preparedStatement.setInt(5, trip.getId());
        preparedStatement.execute();
    }

    public List<Person> getClientList() throws SQLException {
        return getClientsWith("");
    }

    public List<Trip> getTripsList() throws SQLException {
        List<Trip> tripsList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM trip;");
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String egn = resultSet.getString("egn");
            Date arrival = resultSet.getDate("arrival");
            Date departure = resultSet.getDate("departure");
            String destination = resultSet.getString("city");
            tripsList.add(new Trip(id, egn, arrival, departure, destination));
        }
        return tripsList;
    }

    public List<Person> getClientsWith(String nameBeginning) throws SQLException {
        String clientsLike = "SELECT * FROM people WHERE name LIKE '"+nameBeginning+"%';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(clientsLike);
        List<Person> clientList = new ArrayList<>();
        while(resultSet.next()){
            String name = resultSet.getString("name");
            String egn = resultSet.getString("egn");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            clientList.add(new Person(name, egn, age, email));
        }
        return clientList;
    }

    public List<Person> tripsOverlapBetween(Date startDate, Date endDate, String city) throws SQLException {
        String subQuery = "Select people.*, trip.arrival, trip.departure, trip.city from trip inner" +
                " join people on trip.egn=people.egn where arrival<'"+endDate+"' and departure>'"+startDate+"' and city ='"+city+"'";
        String query = "select * from ("+subQuery+") as a inner join ("+subQuery+") as b on a.city= b.city where "
                + " a.egn!=b.egn and a.arrival<b.departure and a.departure>b.arrival;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Person> peopleTripOverlap = new ArrayList<>();
        while(resultSet.next()){
            String name = resultSet.getString("name");
            String egn = resultSet.getString("egn");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            Person person = new Person(name, egn, age, email);
            if (!peopleTripOverlap.contains(person)){
                peopleTripOverlap.add(person);
            }
        }
        return peopleTripOverlap;
    }

    public List<String> citiesByPopularity() throws SQLException {
        String selectCities = "SELECT city FROM trip GROUP BY city ORDER BY count(egn) DESC, city ASC ;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectCities);
        List<String> cities = new ArrayList<>();
        while(resultSet.next()){
            String city = resultSet.getString("city");
            cities.add(city);
        }
        return cities;
    }
}

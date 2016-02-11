package com.clouway.task2.adapter;

import com.clouway.task2.domain.*;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentTripRepository implements TripRepository {
    private final DataSource dataSource;

    public PersistentTripRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void register(TripRequest tripRequest) {
        Person person=tripRequest.getUser();
        try {
            Connection connection=dataSource.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select EGN from people");
            while (resultSet.next()){
                int egn=resultSet.getInt("EGN");
                if (person.getEgn()==egn){
                    register(tripRequest.getTrip(),person.getEgn());
                    return;
                }
            }
            Connection connectionU = null;
            try {
                connectionU = dataSource.getConnection();
                PreparedStatement statementU=connectionU.prepareStatement("insert into people(EGN,age,name,email) values(?,?,?,?)");
                statementU.setInt(1,person.getEgn());
                statementU.setInt(2,person.getAge());
                statementU.setString(3,person.getName());
                statementU.setString(4,person.getEmail());
                statementU.execute();
                register(tripRequest.getTrip(),person.getEgn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void register(Trip trip, int egn){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("insert into trip(EGN,city,date_arrive,departure_date) values(?,?,?,?)");
            statement.setInt(1,egn);
            statement.setString(2,trip.getCity());
            statement.setDate(3,new Date(trip.getDateFrom().getTime()));
            statement.setDate(4,new Date(trip.getDateTo().getTime()));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Person> getPeopleInCityOnDate(String city,  java.util.Date from, java.util.Date to) {
        List<Person> personList=new ArrayList<>();
        Connection connection = null;
        Date dateFrom=new Date(from.getTime());
        Date dateTo=new Date(to.getTime());
        try {
            connection = dataSource.getConnection();
            Statement statement=connection.createStatement();

            ResultSet resultSet1 =statement.executeQuery("SELECT people.EGN, age, name, email FROM people inner JOIN trip ON people.EGN=trip.EGN WHERE city='"+city+"' and date_arrive<='"+dateTo+"' and departure_date>='"+dateFrom+"'");

            while (resultSet1.next()) {
                int egn=resultSet1.getInt("EGN");
                int age=resultSet1.getInt("age");
                String email = resultSet1.getString("email");
                String name = resultSet1.getString("name");
                personList.add(new com.clouway.task2.domain.Person(egn,name,age,email));
            }

            resultSet1.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return personList;
    }

    @Override
    public List<String> mostVisitedCities(){
        Connection connection = null;
        List<String> mostVisitedCities=new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT count(*) AS count,city FROM trip GROUP BY city ORDER BY count DESC");
            while (resultSet.next()) {
                String cityName = resultSet.getString("city");
                mostVisitedCities.add(cityName);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return mostVisitedCities;
    }

    @Override
    public void deleteTrips(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("DELETE FROM trip");
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteRepository(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("DROP TABLE trip");
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

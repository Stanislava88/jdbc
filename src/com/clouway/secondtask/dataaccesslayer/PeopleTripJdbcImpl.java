package com.clouway.secondtask.dataaccesslayer;

import com.clouway.secondtask.core.Person;
import com.clouway.secondtask.core.PersonDao;
import com.clouway.secondtask.core.Trip;
import com.clouway.secondtask.core.TripDao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by clouway on 16-2-24.
 */
public class PeopleTripJdbcImpl implements PersonDao, TripDao {

    private String DB_URL;
    private String user;
    private String password;
    private Connection connection;

    public PeopleTripJdbcImpl(String DB_URL, String user, String password) {
        this.DB_URL = DB_URL;
        this.user = user;
        this.password = password;
    }

    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Person person) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO people (name, egn, age, email) values (?,?,?,?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEgn());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Person person) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE people SET name=?, age=?, email=? WHERE egn=?");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getEgn());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void create(Trip trip) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO trip (egn,arrival,departure,city) values (?,?,?,?)");
            preparedStatement.setString(1, trip.getEgn());
            preparedStatement.setDate(2, new Date(trip.getArrival().getTime()));
            preparedStatement.setDate(3, new Date(trip.getDeparture().getTime()));
            preparedStatement.setString(4, trip.getCity());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Trip trip) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE trip SET arrival=?, departure=?, city=? WHERE egn=?");
            preparedStatement.setDate(1, new Date(trip.getArrival().getTime()));
            preparedStatement.setDate(2, new Date(trip.getDeparture().getTime()));
            preparedStatement.setString(3, trip.getCity());
            preparedStatement.setString(4, trip.getEgn());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Person> getPeople() {
        List<Person> peopleContent = new ArrayList<Person>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM people");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String egn = resultSet.getString("egn");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                peopleContent.add(new Person(name, egn, age, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return peopleContent;

    }

    @Override
    public List<Trip> getTrips() {
        List<Trip> tripsContent = new ArrayList<Trip>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM trip");
            while (resultSet.next()) {
                String egn = resultSet.getString("egn");
                Date arrival = resultSet.getDate("arrival");
                Date departure = resultSet.getDate("departure");
                String city = resultSet.getString("city");
                tripsContent.add(new Trip(egn, arrival, departure, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tripsContent;
    }

    @Override
    public List<Person> getAllPeopleNameWhichStartWithSameCharacters(String phrase) {
        List<Person> peopleNameSameCharacters = new ArrayList<Person>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM people WHERE name LIKE '" + phrase + "%'");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String egn = resultSet.getString("egn");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                peopleNameSameCharacters.add(new Person(name, egn, age, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return peopleNameSameCharacters;
    }

    @Override
    public List<String> getMostVisitedCities() {
        List<String> citiesOrderedByMostVisited = new ArrayList<String>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT city, count(city) counter from trip group by city order by counter desc");
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                citiesOrderedByMostVisited.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return citiesOrderedByMostVisited;
    }

    @Override
    public List<Person> findAllPeopleInTheSameCityAtTheSameTime(java.util.Date arrival, java.util.Date departure) {
        List<Person> peopleInSameCityAtTheSameTime = new ArrayList<Person>();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM people INNER JOIN trip ON people.egn=trip.egn WHERE arrival>='" + arrival + "' AND departure<='" + departure + "'");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String egn = resultSet.getString("egn");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                peopleInSameCityAtTheSameTime.add(new Person(name, egn, age, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return peopleInSameCityAtTheSameTime;
    }

    @Override
    public void deleteTrip(String chooseTableOrContent) {
        Statement stmt = null;
        if (chooseTableOrContent.equalsIgnoreCase("table")) {
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("DROP TABLE trip");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (chooseTableOrContent.equalsIgnoreCase("content")) {
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("DELETE FROM trip");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void deletePeople(String chooseTableOrContent) {
        Statement stmt = null;
        if (chooseTableOrContent.equalsIgnoreCase("table")) {
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("DROP TABLE people");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (chooseTableOrContent.equalsIgnoreCase("content")) {
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("DELETE FROM people");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

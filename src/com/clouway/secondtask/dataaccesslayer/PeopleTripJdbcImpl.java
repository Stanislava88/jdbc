package com.clouway.secondtask.dataaccesslayer;

import com.clouway.secondtask.core.Person;
import com.clouway.secondtask.core.PersonRepository;
import com.clouway.secondtask.core.Trip;
import com.clouway.secondtask.core.TripRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by clouway on 16-2-24.
 */
public class PeopleTripJdbcImpl implements PersonRepository, TripRepository {

    private Connection connection;

    public PeopleTripJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Person person) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO people (name, egn, age, email) values (?,?,?,?)");
            preparedStatement.setString(1, person.name);
            preparedStatement.setString(2, person.egn);
            preparedStatement.setInt(3, person.age);
            preparedStatement.setString(4, person.email);
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
            preparedStatement.setString(1, person.name);
            preparedStatement.setInt(2, person.age);
            preparedStatement.setString(3, person.email);
            preparedStatement.setString(4, person.egn);
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
            preparedStatement.setString(1, trip.egn);
            preparedStatement.setDate(2, new Date(trip.arrival.getTime()));
            preparedStatement.setDate(3, new Date(trip.departure.getTime()));
            preparedStatement.setString(4, trip.city);
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
            preparedStatement.setDate(1, new Date(trip.arrival.getTime()));
            preparedStatement.setDate(2, new Date(trip.departure.getTime()));
            preparedStatement.setString(3, trip.city);
            preparedStatement.setString(4, trip.egn);
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
    public List<Person> findAll() {
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
    public List<Trip> getAll() {
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
        List<Person> people = new ArrayList<Person>();
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
                people.add(new Person(name, egn, age, email));
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
        return people;
    }

    @Override
    public List<String> getMostVisitedCities() {
        List<String> cities = new ArrayList<String>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT city, count(city) counter from trip group by city order by counter desc");
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                cities.add(city);
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
        return cities;
    }

    @Override
    public List<Person> findAllPeopleInTheSameCityAtTheSameTime(java.util.Date arrival, java.util.Date departure) {
        List<Person> people = new ArrayList<Person>();
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
                people.add(new Person(name, egn, age, email));
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
        return people;
    }

    @Override
    public void deleteAll() {
        Statement stmt = null;
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



    @Override
    public void delete() {
        Statement stmt = null;
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



    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

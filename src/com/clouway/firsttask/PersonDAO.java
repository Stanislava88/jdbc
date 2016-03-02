package com.clouway.firsttask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public class PersonDAO {
    private Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    public void register(Person person) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO people (egn,name,age,gender) values (?,?,?,?)");
            stmt.setString(1, person.egn);
            stmt.setString(2, person.name);
            stmt.setInt(3, person.age);
            stmt.setString(4, person.gender);
            stmt.execute();
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

    public List<Person> findAll() {
        Statement stmt = null;
        ResultSet resultSet = null;
        List<Person> people = new ArrayList<Person>();
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM people");
            while (resultSet.next()) {
                String egn = resultSet.getString("egn");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                people.add(new Person(egn, name, age, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return people;
    }


    public void update(Person person) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE people SET name=?, age=?, gender=? WHERE EGN=?");
            stmt.setString(1, person.name);
            stmt.setInt(2, person.age);
            stmt.setString(3, person.gender);
            stmt.setString(4, person.egn);
            stmt.execute();
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

    public void deleteByEgn(String egn) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM people WHERE egn='" + egn + "'");
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


    public void alter(String columname, String columnType) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("ALTER TABLE people ADD " + columname + " " + columnType + "");

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


    public Person findByEgn(String egn) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Person person = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM people WHERE egn=?");
            pstmt.setString(1, egn);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String egn2 = resultSet.getString("egn");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                person = new Person(egn2, name, age, gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return person;
    }


    public List<Person> like(String columName, String wildcard) {
        Statement stmt = null;
        ResultSet resultSet = null;
        List<Person> people = new ArrayList<Person>();
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM people WHERE " + columName + " LIKE '" + wildcard + "'");
            while (resultSet.next()) {
                String egn = resultSet.getString("egn");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                people.add(new Person(egn, name, age, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return people;
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM people");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
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

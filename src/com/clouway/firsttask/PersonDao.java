package com.clouway.firsttask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public class PersonDao {
    private String DB_URL;
    private String user;
    private String password;
    private Connection connection;

    public PersonDao(String DB_URL, String user, String password) {
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

    public void register(Person person) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO peopleinfo (egn,name,age,gender) values (?,?,?,?)");
            stmt.setString(1, person.getEgn());
            stmt.setString(2, person.getName());
            stmt.setInt(3, person.getAge());
            stmt.setString(4, person.getGender());
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
        List<Person> tableContain = new ArrayList<Person>();
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM peopleinfo");
            while (resultSet.next()) {
                String egn = resultSet.getString("egn");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                tableContain.add(new Person(egn, name, age, gender));
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
        return tableContain;
    }


    public void update(Person person) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE peopleinfo SET name=?, age=?, gender=? WHERE EGN=?");
            stmt.setString(1, person.getName());
            stmt.setInt(2, person.getAge());
            stmt.setString(3, person.getGender());
            stmt.setString(4, person.getEgn());
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

    public void delete(String egn) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM peopleinfo WHERE egn='" + egn + "'");
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
            stmt.executeUpdate("ALTER TABLE peopleinfo ADD " + columname + " " + columnType + "");

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
        Statement stmt = null;
        ResultSet resultSet = null;
        Person person = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM peopleinfo WHERE egn='" + egn + "'");
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
        return person;
    }


    public List<Person> like(String columName, String wildcard) {
        Statement stmt = null;
        ResultSet resultSet = null;
        List<Person> tableContain = new ArrayList<Person>();
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM peopleinfo WHERE " + columName + " LIKE '" + wildcard + "'");
            while (resultSet.next()) {
                String egn = resultSet.getString("egn");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                tableContain.add(new Person(egn, name, age, gender));
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
        return tableContain;
    }

    public void deleteTableContent() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM peopleinfo");
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

package com.clouway.jdbc.travel.agency;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Person {
    private final String name;
    private final String egn;
    private final int age;
    private final String email;

    public Person(String name, String egn, int age, String email) {
        this.name = name;
        this.egn = egn;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEgn() {
        return egn;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (egn != null ? !egn.equals(person.egn) : person.egn != null) return false;
        return !(email != null ? !email.equals(person.email) : person.email != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (egn != null ? egn.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", egn='" + egn + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

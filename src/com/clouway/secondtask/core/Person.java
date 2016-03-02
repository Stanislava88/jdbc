package com.clouway.secondtask.core;

/**
 * Created by clouway on 16-2-24.
 */
public class Person {
    public final String name;
    public final String egn;
    public final int age;
    public final String email;

    public Person(String name, String egn, int age, String email) {
        this.name = name;
        this.egn = egn;
        this.age = age;
        this.email = email;
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

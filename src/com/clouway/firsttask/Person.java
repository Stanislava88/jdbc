package com.clouway.firsttask;

/**
 * Created by clouway on 16-3-1.
 */
public class Person {
    public final String egn;
    public final String name;
    public final Integer age;
    public final String gender;

    public Person(String egn, String name, int age, String gender) {
        this.egn = egn;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (egn != null ? !egn.equals(person.egn) : person.egn != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        return !(gender != null ? !gender.equals(person.gender) : person.gender != null);

    }

    @Override
    public int hashCode() {
        int result = egn != null ? egn.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "egn='" + egn + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}

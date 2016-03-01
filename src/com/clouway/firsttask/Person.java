package com.clouway.firsttask;

/**
 * Created by clouway on 16-3-1.
 */
public class Person {
    private String egn;
    private String name;
    private int age;
    private String gender;

    public Person(String egn, String name, int age, String gender) {
        this.egn = egn;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (egn != null ? !egn.equals(person.egn) : person.egn != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return !(gender != null ? !gender.equals(person.gender) : person.gender != null);

    }

    @Override
    public int hashCode() {
        int result = egn != null ? egn.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}

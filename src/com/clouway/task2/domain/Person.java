package com.clouway.task2.domain;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Person {
    private final int egn;
    private final String name;
    private final int age;
    private final String email;

    public Person(int egn, String name, int age, String email) {
        this.egn = egn;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getEgn() {
        return egn;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}

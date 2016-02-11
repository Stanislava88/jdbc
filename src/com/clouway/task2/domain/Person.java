package com.clouway.task2.domain;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Person {
    public final int egn;
    public final String name;
    public final int age;
    public final String email;

    public Person(int egn, String name, int age, String email) {
        this.egn = egn;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}

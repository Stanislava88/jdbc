package com.clouway.task2.domain;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class TripRequest {
    private final Person person;
    private final Trip trip;

    public TripRequest(Person person, Trip trip){
        this.person = person;
        this.trip = trip;
    }

    public Person getUser() {
        return person;
    }

    public Trip getTrip() {
        return trip;
    }
}

package com.clouway.task2.domain;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class TripRequest {
    public final Person person;
    public final Trip trip;

    public TripRequest(Person person, Trip trip){
        this.person = person;
        this.trip = trip;
    }
}

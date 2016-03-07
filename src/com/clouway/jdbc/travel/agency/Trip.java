package com.clouway.jdbc.travel.agency;


import java.sql.Date;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Trip {
    private final int id;
    private final String egn;
    private final Date arrival;
    private final Date departure;
    private final String destination;

    public Trip(int id, String egn, Date arrival, Date departure, String destination) {
        this.id = id;
        this.egn = egn;
        this.arrival = arrival;
        this.departure = departure;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public String getEgn() {
        return egn;
    }

    public Date getArrival() {
        return arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (id != trip.id) return false;
        if (egn != null ? !egn.equals(trip.egn) : trip.egn != null) return false;
        if (arrival != null ? !arrival.equals(trip.arrival) : trip.arrival != null) return false;
        if (departure != null ? !departure.equals(trip.departure) : trip.departure != null) return false;
        return !(destination != null ? !destination.equals(trip.destination) : trip.destination != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (egn != null ? egn.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", egn='" + egn + '\'' +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", destination='" + destination + '\'' +
                '}';
    }
}

package com.clouway.tripagency.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Trip {
  public final String egn;
  public final long dateArrived;
  public final long dateDeparture;
  public final String city;

  public Trip(String egn, long dateArrived, long dateDeparture, String city) {
    this.egn = egn;
    this.dateArrived = dateArrived;
    this.dateDeparture = dateDeparture;
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Trip trip = (Trip) o;

    if (dateArrived != trip.dateArrived) return false;
    if (dateDeparture != trip.dateDeparture) return false;
    if (egn != null ? !egn.equals(trip.egn) : trip.egn != null) return false;
    return city != null ? city.equals(trip.city) : trip.city == null;

  }

  @Override
  public int hashCode() {
    int result = egn != null ? egn.hashCode() : 0;
    result = 31 * result + (int) (dateArrived ^ (dateArrived >>> 32));
    result = 31 * result + (int) (dateDeparture ^ (dateDeparture >>> 32));
    result = 31 * result + (city != null ? city.hashCode() : 0);
    return result;
  }
}

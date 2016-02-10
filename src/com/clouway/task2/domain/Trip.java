package com.clouway.task2.domain;

import java.util.Date;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Trip {
    private final String city;
    private final Date dateFrom;
    private final Date dateTo;

    public Trip(String city, Date dateFrom, Date dateTo){
        this.city = city;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getCity() {
        return city;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }
}

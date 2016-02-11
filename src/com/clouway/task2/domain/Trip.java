package com.clouway.task2.domain;

import java.util.Date;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Trip {
    public final String city;
    public final Date dateFrom;
    public final Date dateTo;

    public Trip(String city, Date dateFrom, Date dateTo){
        this.city = city;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}

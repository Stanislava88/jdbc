package com.clouway.jdbc.customer.history.persistence;

import java.sql.Timestamp;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class CustomerRecord {
    public final int recordId;
    public final Timestamp changeDate;
    public final int customerId;
    public final String name;
    public final String lastName;
    public final String egn;

    public CustomerRecord(int recordId, Timestamp changeDate, int customerId, String name, String lastName, String egn) {
        this.recordId = recordId;
        this.changeDate = changeDate;
        this.customerId = customerId;
        this.name = name;
        this.lastName = lastName;
        this.egn = egn;
    }
}

package com.clouway.jdbc.customer.history.persistence;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface CustomerHistoryRepository {

    CustomerRecord getLastUpdate(int id);

    List<CustomerRecord> getById(int id);

    List<CustomerRecord> getUpdates();
}

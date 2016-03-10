package com.clouway.jdbc.travel.agency;

import com.clouway.jdbc.travel.agency.persistence.PersistentClientRepository;
import com.clouway.jdbc.travel.agency.persistence.PersistentTripRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class TravelAgency {
    private final PersistentClientRepository persistentClientRepository;
    private final PersistentTripRepository persistentTripRepository;
    private ClientsTripInfo clientsTripInfo;

    public TravelAgency(PersistentClientRepository persistentClientRepository, PersistentTripRepository persistentTripRepository, ClientsTripInfo clientsTripInfo) {
        this.persistentClientRepository = persistentClientRepository;
        this.persistentTripRepository = persistentTripRepository;
        this.clientsTripInfo = clientsTripInfo;
    }

    public void registerClient(Client client) throws SQLException {
        persistentClientRepository.register(client);
    }

    public Client getClient(String egn) throws SQLException {
        return persistentClientRepository.getByEgn(egn);
    }

    public void bookTrip(Trip trip) throws SQLException {
        persistentTripRepository.book(trip);
    }

    public Trip getTrip(int id) throws SQLException {
        return persistentTripRepository.getById(id);
    }

    public void updateClient(Client client) throws SQLException {
        persistentClientRepository.update(client);
    }

    public void updateTrip(Trip trip) throws SQLException {
        persistentTripRepository.update(trip);
    }

    public List<Client> getClientsList() throws SQLException {
        return persistentClientRepository.getClientsList();
    }

    public List<Trip> getTripsList() throws SQLException {
        return persistentTripRepository.getList();
    }

    public List<Client> getClientsWith(String nameBeginning) throws SQLException {
        return persistentClientRepository.getClientsWith(nameBeginning);
    }

    public List<Client> tripsOverlapBetween(Date startDate, Date endDate, String city) throws SQLException {
        return clientsTripInfo.tripsOverlapBetween(startDate, endDate, city);
    }

    public List<String> citiesByPopularity() throws SQLException {
        return persistentTripRepository.citiesByPopularity();
    }
}

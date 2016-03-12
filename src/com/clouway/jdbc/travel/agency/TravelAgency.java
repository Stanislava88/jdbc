package com.clouway.jdbc.travel.agency;

import com.clouway.jdbc.travel.agency.persistence.PersistentClientRepository;
import com.clouway.jdbc.travel.agency.persistence.PersistentTripRepository;

import java.sql.Date;
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

    public void registerClient(Client client) {
        persistentClientRepository.register(client);
    }

    public Client getClient(String egn) {
        return persistentClientRepository.getByEgn(egn);
    }

    public void bookTrip(Trip trip) {
        persistentTripRepository.book(trip);
    }

    public Trip getTrip(int id) {
        return persistentTripRepository.getById(id);
    }

    public void updateClient(Client client) {
        persistentClientRepository.update(client);
    }

    public void updateTrip(Trip trip) {
        persistentTripRepository.update(trip);
    }

    public List<Client> getClientsList() {
        return persistentClientRepository.getClientsList();
    }

    public List<Trip> getTripsList() {
        return persistentTripRepository.getList();
    }

    public List<Client> getClientsWith(String nameBeginning) {
        return persistentClientRepository.getClientsWith(nameBeginning);
    }

    public List<Client> tripsOverlapBetween(Date startDate, Date endDate, String city) {
        return clientsTripInfo.peopleTripsOverlapBetween(startDate, endDate, city);
    }

    public List<String> citiesByPopularity() {
        return persistentTripRepository.citiesByPopularity();
    }
}

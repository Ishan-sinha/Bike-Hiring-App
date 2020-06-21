package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.Ride;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class RideDAO extends AbstractDAO<Ride> {

    public RideDAO(SessionFactory factory) {
        super(factory);
    }

    public Ride start(Ride ride) {
        return persist(ride);
    }

    public Ride getById(int rideId) {
        return get(rideId);
    }

    public Ride end(Ride ride) {
        return persist(ride);
    }

    @SuppressWarnings("unchecked")
    public List<Ride> findByPersonId(int id) {
        return list(namedQuery("Ride.findByPersonId").setParameter("personId",
                id
        ));
    }

}

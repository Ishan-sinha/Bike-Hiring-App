package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.Cycle;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class CycleDAO extends AbstractDAO<Cycle> {

    public CycleDAO(SessionFactory factory) {
        super(factory);
    }

    public Cycle create(Cycle cycle) {
        cycle.setStatus(1);
        return persist(cycle);
    }

    @SuppressWarnings("unchecked")
    public List<Cycle> findAll() {
        return list(namedQuery("Cycle.findAll"));
    }

    @SuppressWarnings("unchecked")
    public List<Cycle> getCyclesByOwnerId(int ownerId) {
        return list(namedQuery("Cycle.findByPersonId").setParameter("ownerId",
                ownerId
        ));
    }

    public Cycle remove(Integer id) {
        Cycle cycle
                = findById(id).orElseThrow(() -> new WebApplicationException("Cycle not found \"" + id + "\"",
                Response.Status.NOT_FOUND
        ));
        cycle.setStatus(0);
        return persist(cycle);
    }

    public Optional<Cycle> findById(Integer id) {
        return Optional.ofNullable(get(id));
    }

}

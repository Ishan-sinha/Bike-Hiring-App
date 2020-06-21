package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.Complaint;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ComplaintDAO extends AbstractDAO<Complaint> {

    public ComplaintDAO(SessionFactory factory) {
        super(factory);
    }

    public Complaint create(Complaint complaint) {
        return persist(complaint);
    }

    @SuppressWarnings("unchecked")
    public List<Complaint> findAll() {
        return list(namedQuery("Complaint.findAll"));
    }

    public Optional<Complaint> getById(int complaintId) {
        return Optional.ofNullable(get(complaintId));
    }

}

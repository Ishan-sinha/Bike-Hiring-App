package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PersonDAO extends AbstractDAO<Person> {

    public PersonDAO(SessionFactory factory) {
        super(factory);
    }

    public Person create(Person person) {
        return persist(person);
    }

    @SuppressWarnings("unchecked")
    public List<Person> findAll() {
        return list(namedQuery("Person.findAll"));
    }

    public Optional<Person> findById(Integer id) {
        return Optional.ofNullable(get(id));
    }

}

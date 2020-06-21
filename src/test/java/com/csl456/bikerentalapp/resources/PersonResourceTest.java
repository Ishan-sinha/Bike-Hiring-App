package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Person;
import com.csl456.bikerentalapp.db.PersonDAO;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class PersonResourceTest {

    private static final PersonDAO         PERSON_DAO = mock(PersonDAO.class);
    private static final ResourceExtension RESOURCES  = ResourceExtension
            .builder()
            .addResource(new PersonResource(PERSON_DAO))
            .build();

    private final ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(
            Person.class);

    private Person person;

    @Test
    void createPerson() {
        when(PERSON_DAO.create(any(Person.class))).thenReturn(person);
        final Response response = RESOURCES
                .target("/person")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(person, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(PERSON_DAO).create(personCaptor.capture());
        assertThat(personCaptor.getValue()).isEqualTo(person);
    }

    @Test
    void listPeople() {
        final List<Person> people = Collections.singletonList(person);
        when(PERSON_DAO.findAll()).thenReturn(people);
        final List<Person> response = RESOURCES
                .target("/person")
                .request()
                .get(new GenericType<List<Person>>() {});
        verify(PERSON_DAO).findAll();
        assertThat(response).containsAll(people);
    }

    @BeforeEach
    void setUp() {
        person = new Person("Aditya Gupta", 1234567890L, "aditya@example.com");
    }

    @AfterEach
    void tearDown() { reset(PERSON_DAO);}

}

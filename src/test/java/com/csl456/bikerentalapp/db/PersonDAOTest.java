package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.Cycle;
import com.csl456.bikerentalapp.core.Person;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PersonDAOTest {

    private final DAOTestExtension daoTestRule = DAOTestExtension
            .newBuilder()
            .addEntityClass(Person.class)
            .addEntityClass(Cycle.class)
            .build();

    private PersonDAO personDAO;

    @Test
    public void createPerson() {
        final Person person = daoTestRule.inTransaction(() -> personDAO.create(
                new Person("Aditya Gupta", 1234567890L, "aditya@example.com")));
        assertThat(person.getId()).isGreaterThan(0);
        assertThat(person.getName()).isEqualTo("Aditya Gupta");
        assertThat(person.getContactNumber()).isEqualTo(1234567890L);
        assertThat(person.getEmail()).isEqualTo("aditya@example.com");
        assertThat(personDAO.findById(person.getId())).isEqualTo(Optional.of(
                person));
    }

    @Test
    public void findAll() {
        daoTestRule.inTransaction(() -> {
            personDAO.create(new Person("Aditya",
                    1234567890L,
                    "aditya@example.com"
            ));
            personDAO.create(new Person("Vinit",
                    2345678901L,
                    "vinit@example.com"
            ));
            personDAO.create(new Person("Piyush",
                    3456789012L,
                    "piyush@example.com"
            ));
            personDAO.create(new Person("Sachin",
                    4567890123L,
                    "sachin@example.com"
            ));
        });

        final List<Person> persons = personDAO.findAll();
        assertThat(persons)
                .extracting("name")
                .containsOnly("Aditya", "Vinit", "Piyush", "Sachin");
        assertThat(persons)
                .extracting("contactNumber")
                .containsOnly(1234567890L,
                        2345678901L,
                        3456789012L,
                        4567890123L
                );
        assertThat(persons).extracting("email").containsOnly(
                "aditya@example.com",
                "vinit@example.com",
                "piyush@example.com",
                "sachin@example.com"
        );
    }

    @Test
    public void handlesNullName() {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> daoTestRule.inTransaction(() -> personDAO.create(new Person(
                        null,
                        1234567890L,
                        "aditya@example.com"
                ))));
    }

    @BeforeEach
    public void setUp() {
        personDAO = new PersonDAO(daoTestRule.getSessionFactory());
    }

}

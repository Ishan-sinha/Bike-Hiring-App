package com.csl456.bikerentalapp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void deserializesFromJSON() throws Exception {
        assertEquals(MAPPER.readValue(fixture("fixtures/person.json"),
                Person.class
        ), getPerson());
    }

    private static Person getPerson() {
        return new Person("Aditya Gupta", 1234567890L, "aditya@example.com");
    }

}

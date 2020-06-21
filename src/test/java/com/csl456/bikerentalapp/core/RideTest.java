package com.csl456.bikerentalapp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class RideTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(
                fixture("fixtures/ride.json"),
                Ride.class
        )).isEqualTo(getRide());
    }

    public static Ride getRide() {
        Date date = new Date(0);
        return new Ride(1, 2, date, date, 1, 1, 10.0);
    }

}

package com.csl456.bikerentalapp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/location.json"),
                Location.class
        )).isEqualTo(getLocation());
    }

    public static Location getLocation() {
        return new Location("SATLUJ_HOSTEL", 5, 7);
    }

}

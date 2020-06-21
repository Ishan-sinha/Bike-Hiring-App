package com.csl456.bikerentalapp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class CycleTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(
                fixture("fixtures/cycle.json"),
                Cycle.class
        )).isEqualTo(getCycle());
    }

    public static Cycle getCycle() { return new Cycle("Atlas", 1, 1, 1);}

}

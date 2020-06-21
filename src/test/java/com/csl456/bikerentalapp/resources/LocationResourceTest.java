package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Location;
import com.csl456.bikerentalapp.db.LocationDAO;
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
class LocationResourceTest {

    private static final LocationDAO       LOCATION_DAO
                                                     = mock(LocationDAO.class);
    private static final ResourceExtension RESOURCES = ResourceExtension
            .builder()
            .addResource(new LocationResource(LOCATION_DAO))
            .build();

    private final ArgumentCaptor<Location> locationCaptor
            = ArgumentCaptor.forClass(Location.class);

    private Location location;

    @Test
    void createLocation() {
        when(LOCATION_DAO.create(any(Location.class))).thenReturn(location);
        final Response response = RESOURCES
                .target("/location")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(location, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(LOCATION_DAO).create(locationCaptor.capture());
        assertThat(locationCaptor.getValue()).isEqualTo(location);
    }

    @Test
    void listLocations() {
        final List<Location> locations = Collections.singletonList(location);
        when(LOCATION_DAO.findAll()).thenReturn(locations);
        final List<Location> response = RESOURCES
                .target("/location")
                .request()
                .get(new GenericType<List<Location>>() {});
        verify(LOCATION_DAO).findAll();
        assertThat(response).containsAll(locations);
    }

    @BeforeEach
    void setUp() { location = new Location("SATLUJ_HOSTEL", 5, 6);}

    @AfterEach
    void tearDown() { reset(LOCATION_DAO);}

}

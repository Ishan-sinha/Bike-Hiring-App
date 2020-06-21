package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Ride;
import com.csl456.bikerentalapp.db.RideDAO;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class RideResourceTest {

    private static final RideDAO RIDE_DAO = mock(RideDAO.class);

    private static final ResourceExtension RESOURCES = ResourceExtension
            .builder()
            .addResource(new RideResource(RIDE_DAO))
            .build();

    private final ArgumentCaptor<Ride> rideCaptor
            = ArgumentCaptor.forClass(Ride.class);

    private Ride startRide, endRide;

    @Test
    void startRide() {
        when(RIDE_DAO.start(any(Ride.class))).thenReturn(startRide);
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("startLocationId", "1");
        formData.add("personId", "2");
        formData.add("cycleId", "1");
        final Response response = RESOURCES
                .target("/ride/start")
                .request()
                .post(Entity.form(formData));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(RIDE_DAO).start(rideCaptor.capture());
        Ride result = rideCaptor.getValue();
        assertThat(result.getStartLocationId()).isEqualTo(startRide.getStartLocationId());
        assertThat(result.getEndLocationId()).isEqualTo(startRide.getEndLocationId());
        assertThat(result.getCycleId()).isEqualTo(startRide.getCycleId());
        assertThat(result.getPersonId()).isEqualTo(startRide.getPersonId());
    }

    @Test
    void endRide() {
        when(RIDE_DAO.getById(startRide.getId())).thenReturn(startRide);
        when(RIDE_DAO.end(any(Ride.class))).thenReturn(endRide);
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("rideId", String.valueOf(startRide.getId()));
        formData.add("endLocationId", "2");
        final Response response = RESOURCES
                .target("/ride/end")
                .request()
                .post(Entity.form(formData));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(RIDE_DAO).end(rideCaptor.capture());
        Ride result = rideCaptor.getValue();
        assertThat(result.getEndLocationId()).isEqualTo(startRide.getEndLocationId());
        assertThat(result.getEndTime()).isNotNull();
        assertThat(result.getCost()).isGreaterThan(0);
    }

    @Test
    void listRidesByPersonId() {
        final List<Ride> rides = Collections.singletonList(endRide);
        when(RIDE_DAO.findByPersonId(2)).thenReturn(rides);
        final List<Ride> response = RESOURCES
                .target("/ride")
                .queryParam("personId", 2)
                .request()
                .get(new GenericType<List<Ride>>() {});
        verify(RIDE_DAO).findByPersonId(2);
        assertThat(response.size()).isEqualTo(1);
        Ride result = response.get(0);
        assertThat(result.getPersonId()).isEqualTo(2);
    }

    @BeforeEach
    void setup() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd-MMM-yyyy " + "HH:mm:ss");

        Date startDate = formatter.parse("15-Jan-2019 15:00:00");
        Date endDate   = formatter.parse("15-Jan-2019 16:00:00");

        startRide = new Ride(1, 0, startDate, null, 1, 2, 0);
        endRide   = new Ride(1, 2, startDate, endDate, 1, 2, 10.0);
    }

    @AfterEach
    void tearDown() { reset(RIDE_DAO);}

}

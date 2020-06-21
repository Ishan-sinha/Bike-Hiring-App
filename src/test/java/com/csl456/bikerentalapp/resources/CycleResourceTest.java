package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Cycle;
import com.csl456.bikerentalapp.db.CycleDAO;
import com.csl456.bikerentalapp.db.SessionDAO;
import com.csl456.bikerentalapp.db.UserDAO;
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
class CycleResourceTest {

    private static final CycleDAO   CYCLE_DAO   = mock(CycleDAO.class);
    private static final UserDAO    USER_DAO    = mock(UserDAO.class);
    private static final SessionDAO SESSION_DAO = mock(SessionDAO.class);

    private static final ResourceExtension RESOURCES = ResourceExtension
            .builder()
            .addResource(new CycleResource(CYCLE_DAO, USER_DAO, SESSION_DAO))
            .build();

    private final ArgumentCaptor<Cycle> cycleCaptor = ArgumentCaptor.forClass(
            Cycle.class);

    private Cycle cycle;

    @Test
    void createCycle() {
        when(CYCLE_DAO.create(any(Cycle.class))).thenReturn(cycle);
        final Response response = RESOURCES
                .target("/cycle")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(cycle, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(CYCLE_DAO).create(cycleCaptor.capture());
        assertThat(cycleCaptor.getValue()).isEqualTo(cycle);
    }

    @Test
    void listCycles() {
        //TODO get cycle by ownerid
        final List<Cycle> cycles = Collections.singletonList(cycle);
        when(CYCLE_DAO.findAll()).thenReturn(cycles);
        final List<Cycle> response = RESOURCES
                .target("/cycle")
                .request()
                .get(new GenericType<List<Cycle>>() {});
        verify(CYCLE_DAO).findAll();
        assertThat(response).containsAll(cycles);
    }

    //TODO get cycle by id
    //TODO remove cycle
    //TODO change location

    @BeforeEach
    void setUp() { cycle = new Cycle("Atlas", 1, 1, 1);}

    @AfterEach
    void tearDown() { reset(CYCLE_DAO);}

}

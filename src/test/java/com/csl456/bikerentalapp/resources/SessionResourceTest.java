package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Person;
import com.csl456.bikerentalapp.core.Session;
import com.csl456.bikerentalapp.core.User;
import com.csl456.bikerentalapp.core.UserRole;
import com.csl456.bikerentalapp.db.PersonDAO;
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
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class SessionResourceTest {

    private static final SessionDAO SESSION_DAO = mock(SessionDAO.class);
    private static final UserDAO    USER_DAO    = mock(UserDAO.class);
    private static final PersonDAO  PERSON_DAO  = mock(PersonDAO.class);

    private static final ResourceExtension RESOURCES = ResourceExtension
            .builder()
            .addResource(new SessionResource(USER_DAO, SESSION_DAO, PERSON_DAO))
            .build();

    private final ArgumentCaptor<Session> sessionCaptor
            = ArgumentCaptor.forClass(Session.class);

    private Session session;
    private User    user;
    private Person  person;

    @Test
    void login() {
        //TODO invalid login
        when(USER_DAO.findUsersByUsernameAndPassword("aditya",
                "abc"
        )).thenReturn(user);
        when(PERSON_DAO.findById(1)).thenReturn(Optional.of(person));
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("username", "aditya");
        formData.add("password", "abc");
        final Response response = RESOURCES
                .target("/session")
                .request()
                .post(Entity.form(formData));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(SESSION_DAO).insert(sessionCaptor.capture());
        Session capturedSession = sessionCaptor.getValue();
        assertThat(capturedSession.getIdentity()).isEqualTo(session.getIdentity());
    }

    @Test
    void logout() {
        when(SESSION_DAO.remove(anyString())).thenReturn(1);
        final Response response = RESOURCES
                .target("/session")
                .request()
                .delete();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @BeforeEach
    void setUp() {
        person = new Person("Aditya Gupta", 1234567890L, "aditya@example.com");
        user = new User("aditya", "abc", UserRole.NORMAL_USER, 1);
        session = new Session("aditya");
    }

    @AfterEach
    void tearDown() { reset(SESSION_DAO);}

}

package com.csl456.bikerentalapp.resources;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UserResourceTest {

    private static final SessionDAO SESSION_DAO = mock(SessionDAO.class);
    private static final UserDAO    USER_DAO    = mock(UserDAO.class);
    private static final PersonDAO  PERSON_DAO  = mock(PersonDAO.class);

    private static final ResourceExtension RESOURCES = ResourceExtension
            .builder()
            .addResource(new SessionResource(USER_DAO, SESSION_DAO, PERSON_DAO))
            .build();

    private final ArgumentCaptor<Session> sessionCaptor
            = ArgumentCaptor.forClass(Session.class);


    @BeforeEach
    void setUp() {
        User    user    = new User("aditya", "abc", UserRole.NORMAL_USER, 1);
        Session session = new Session("aditya");
    }

    @AfterEach
    void tearDown() {
        reset(SESSION_DAO);
    }

    //TODO

}

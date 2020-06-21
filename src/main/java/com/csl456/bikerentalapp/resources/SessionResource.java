package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Person;
import com.csl456.bikerentalapp.core.Session;
import com.csl456.bikerentalapp.core.User;
import com.csl456.bikerentalapp.core.UserInformation;
import com.csl456.bikerentalapp.db.PersonDAO;
import com.csl456.bikerentalapp.db.SessionDAO;
import com.csl456.bikerentalapp.db.UserDAO;
import com.csl456.bikerentalapp.filter.LoggedIn;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

@Path("session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private final UserDAO    userDAO;
    private final SessionDAO sessionDAO;
    private final PersonDAO  personDAO;

    public SessionResource(UserDAO userDAO, SessionDAO sessionDAO,
            PersonDAO personDAO) {
        this.userDAO    = userDAO;
        this.sessionDAO = sessionDAO;
        this.personDAO  = personDAO;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserInformation login(@FormParam("username") String username,
            @FormParam("password") String password) {
        User user = userDAO.findUsersByUsernameAndPassword(username, password);
        if (user == null) {
            throw new WebApplicationException("Username or Password Wrong",
                    Status.UNAUTHORIZED
            );
        }
        Person person = personDAO
                .findById(user.getPersonId())
                .orElseThrow(() -> new WebApplicationException(
                        "No Person Found for this User",
                        Status.NOT_FOUND
                ));
        Session session = new Session(username);
        sessionDAO.insert(session);
        return new UserInformation(session, user, person);
    }

    @DELETE
    @UnitOfWork
    @LoggedIn
    public int logout(@HeaderParam("Access_Token") String accessToken) {
        return sessionDAO.remove(accessToken);
    }

}

package com.csl456.bikerentalapp.filter;

import com.csl456.bikerentalapp.db.SessionDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class LoggedInFilter implements ContainerRequestFilter {

    private final SessionDAO sessionDAO;

    public LoggedInFilter(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    @UnitOfWork
    public void filter(ContainerRequestContext requestContext) {
        final String accessToken
                = requestContext.getHeaderString("Access_Token");
        if (sessionDAO.notLoggedIn(accessToken)) {
            throw new WebApplicationException("You are not logged in.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

}

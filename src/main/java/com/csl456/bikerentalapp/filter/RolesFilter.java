package com.csl456.bikerentalapp.filter;

import com.csl456.bikerentalapp.core.User;
import com.csl456.bikerentalapp.core.UserRole;
import com.csl456.bikerentalapp.db.SessionDAO;
import com.csl456.bikerentalapp.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class RolesFilter implements ContainerRequestFilter {

    private final UserDAO        userDAO;
    private final SessionDAO     sessionDAO;
    private final List<UserRole> allowedRoles;

    public RolesFilter(SessionDAO sessionDAO, UserDAO userDAO,
            UserRole[] allowedRoles) {
        this.sessionDAO   = sessionDAO;
        this.userDAO      = userDAO;
        this.allowedRoles = Arrays.asList(allowedRoles);
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
        String username = sessionDAO.getUserName(accessToken);
        User   user     = userDAO.findByUserName(username);
        if (!allowedRoles.contains(user.getRole())) {
            throw new WebApplicationException(
                    "You do not have sufficient allowed Roles.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

}

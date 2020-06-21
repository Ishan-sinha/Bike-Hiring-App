package com.csl456.bikerentalapp.filter;

import com.csl456.bikerentalapp.BikeRentalAppConfiguration;
import com.csl456.bikerentalapp.core.UserRole;
import com.csl456.bikerentalapp.db.SessionDAO;
import com.csl456.bikerentalapp.db.UserDAO;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class RolesAllowedFeature implements DynamicFeature {

    private final SessionDAO                                  sessionDAO;
    private final UserDAO                                     userDAO;
    private final HibernateBundle<BikeRentalAppConfiguration> hibernateBundle;

    public RolesAllowedFeature(
            HibernateBundle<BikeRentalAppConfiguration> hibernateBundle,
            SessionDAO sessionDAO, UserDAO userDAO) {
        this.hibernateBundle = hibernateBundle;
        this.sessionDAO      = sessionDAO;
        this.userDAO         = userDAO;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        RolesAllowed roles = resourceInfo
                .getResourceMethod()
                .getAnnotation(RolesAllowed.class);
        if (roles != null) {
            RolesFilter rolesFilter = new UnitOfWorkAwareProxyFactory(
                    hibernateBundle).create(RolesFilter.class, new Class<?>[]{
                    SessionDAO.class, UserDAO.class, UserRole[].class
            }, new Object[]{sessionDAO, userDAO, roles.value()});
            context.register(rolesFilter);
        }
    }

}
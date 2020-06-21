package com.csl456.bikerentalapp.filter;

import com.csl456.bikerentalapp.BikeRentalAppConfiguration;
import com.csl456.bikerentalapp.db.SessionDAO;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class LoggedInFeature implements DynamicFeature {

    private final HibernateBundle<BikeRentalAppConfiguration> hibernateBundle;

    private final SessionDAO sessionDAO;

    public LoggedInFeature(
            HibernateBundle<BikeRentalAppConfiguration> hibernateBundle,
            SessionDAO sessionDAO) {
        this.hibernateBundle = hibernateBundle;
        this.sessionDAO      = sessionDAO;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (resourceInfo.getResourceMethod().getAnnotation(LoggedIn.class)
                != null) {
            LoggedInFilter loggedInFilter = new UnitOfWorkAwareProxyFactory(
                    hibernateBundle).create(LoggedInFilter.class,
                    SessionDAO.class,
                    sessionDAO
            );
            context.register(loggedInFilter);
        }
    }

}

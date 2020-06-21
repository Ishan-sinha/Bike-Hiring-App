package com.csl456.bikerentalapp.db;


import com.csl456.bikerentalapp.core.Session;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class SessionDAO extends AbstractDAO<Session> {

    public SessionDAO(SessionFactory factory) {
        super(factory);
    }

    public void insert(Session session) {
        persist(session);
    }

    public int remove(String accessToken) {
        return namedQuery("Session.remove")
                .setParameter("accessToken", accessToken)
                .executeUpdate();
    }

    public void removeAll(String username) {
        namedQuery("Session.removeAll")
                .setParameter("username", username)
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public boolean notLoggedIn(String accessToken) {
        return uniqueResult(namedQuery("Session.loggedIn").setParameter("accessToken",
                accessToken
        )) == null;
    }

    @SuppressWarnings("unchecked")
    public String getUserName(String accessToken) {
        return uniqueResult(namedQuery("Session.loggedIn").setParameter("accessToken",
                accessToken
        )).getIdentity();
    }

}
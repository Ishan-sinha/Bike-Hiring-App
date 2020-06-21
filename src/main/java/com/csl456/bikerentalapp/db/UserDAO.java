package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    public User create(User user) {
        return persist(user);
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

    @SuppressWarnings("unchecked")
    public User findUsersByUsernameAndPassword(String username,
            String password) {
        return uniqueResult(namedQuery("User.findByUserNameAndPassword")
                .setParameter("username", username)
                .setParameter("password", password));
    }

    @SuppressWarnings("unchecked")
    public User findByUserName(String username) {
        return uniqueResult(namedQuery("User.findByUserName").setParameter("username",
                username
        ));
    }

    public void changePassword(String username, String newPassword) {
        namedQuery("User.changePassword")
                .setParameter("username", username)
                .setParameter("password", newPassword)
                .executeUpdate();
    }

}

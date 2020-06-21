package com.csl456.bikerentalapp.core;

public class UserInformation {

    private Session session;
    private User    user;
    private Person  person;

    public UserInformation(Session session, User user, Person person) {
        this.session = session;
        this.user    = user;
        this.person  = person;
    }

    public UserInformation() {
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}

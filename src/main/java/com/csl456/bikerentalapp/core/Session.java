package com.csl456.bikerentalapp.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "session")
@NamedQueries({
        @NamedQuery(name = "Session.removeAll",
                query = "DELETE FROM Session S WHERE S.identity = :username"),
        @NamedQuery(name = "Session.loggedIn",
                query = "SELECT S FROM Session S WHERE S.accessToken = :accessToken"),
        @NamedQuery(name = "Session.remove",
                query = "DELETE FROM Session S WHERE S.accessToken = :accessToken")
})
public class Session {

    @Id
    private String accessToken;

    @Column(name = "username", nullable = false)
    private String identity;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date created;

    public Session() {}

    public Session(String username) {
        this.identity    = username;
        this.accessToken = UUID.randomUUID().toString().substring(0, 23);
        this.created     = new Date();
    }

    public String getIdentity() { return identity;}

    public String getAccessToken() { return accessToken;}

    public Date getCreated() { return created;}

}

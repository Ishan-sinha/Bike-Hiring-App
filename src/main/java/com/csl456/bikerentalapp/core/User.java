package com.csl456.bikerentalapp.core;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT U FROM User U"),
        @NamedQuery(name = "User.findByUserNameAndPassword",
                query = "SELECT U FROM User U WHERE U.username = :username AND U.password = :password"),
        @NamedQuery(name = "User.findByUserName",
                query = "SELECT U FROM User U WHERE U.username = :username"),
        @NamedQuery(name = "User.changePassword",
                query = "UPDATE User U SET U.password = :password WHERE U.username = :username")
})
public class User {

    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false)
    private int personId;

    public User() {}

    public User(String username, String password, UserRole role, int personId) {
        this.username = username;
        this.password = password;
        this.role     = role;
        this.personId = personId;
    }

    public int getPersonId() { return personId;}

    public void setPersonId(int personId) { this.personId = personId;}

    @Override
    public int hashCode() {
        return Objects.hashCode(username, password, role, personId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(username, user.username) && Objects.equal(password,
                user.password
        ) && role == user.role && Objects.equal(
                personId,
                user.personId
        );
    }

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    public UserRole getRole() { return role;}

    public void setRole(UserRole role) { this.role = role;}

}

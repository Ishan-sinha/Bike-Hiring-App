package com.csl456.bikerentalapp.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "cycle")
@NamedQueries({
        @NamedQuery(name = "Cycle.findAll",
                query = "SELECT C FROM " + "Cycle C"),
        @NamedQuery(name = "Cycle.findById",
                query = "SELECT C FROM Cycle C WHERE C.id = :id"),
        @NamedQuery(name = "Cycle.deleteById",
                query = "DELETE FROM Cycle C WHERE C.id = :id"),
        @NamedQuery(name = "Cycle.findByPersonId",
                query = "SELECT C FROM Cycle C WHERE C.ownerId = :ownerId")
})
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int    id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private int    locationId;
    @Column(nullable = false)
    private int    ownerId;
    @Column(nullable = false)
    private int    status;

    public Cycle() {}

    public Cycle(String brand, int locationId, int ownerId, int status) {
        this.brand      = brand;
        this.locationId = locationId;
        this.ownerId    = ownerId;
        this.status     = status;
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getLocationId() { return locationId;}

    public void setLocationId(int locationId) { this.locationId = locationId;}

    @Override
    public int hashCode() { return Objects.hash(brand, locationId, ownerId);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Cycle other = (Cycle) obj;
        return Objects.equals(brand, other.brand)
                && locationId == other.locationId && ownerId == other.ownerId;
    }

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public int getOwnerId() { return ownerId;}

    public void setOwnerId(int ownerId) { this.ownerId = ownerId;}

    public void setPersonId(int ownerId) { this.ownerId = ownerId;}

}

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
@Table(name = "locations")
@NamedQueries({
        @NamedQuery(name = "Location.findAll",
                query = "SELECT L FROM Location L"),
        @NamedQuery(name = "Location.findById",
                query = "SELECT L FROM Location L WHERE L.id = :id")
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int    id;
    @Column
    private String name;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name      = name;
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    public Location() {}

    public double getLongitude() { return longitude;}

    public void setLongitude(double longitude) { this.longitude = longitude;}

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public double getLatitude() { return latitude;}

    public void setLatitude(double latitude) { this.latitude = latitude;}

    @Override
    public int hashCode() { return Objects.hash(longitude, latitude, name);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Location other = (Location) obj;
        return id == other.id && Double.doubleToLongBits(longitude)
                == Double.doubleToLongBits(other.longitude)
                && Double.doubleToLongBits(latitude) == Double.doubleToLongBits(
                other.latitude) && Objects.equals(name, other.name);
    }

}

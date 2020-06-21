package com.csl456.bikerentalapp.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "Ride.findByPersonId",
                query = "SELECT R FROM Ride R WHERE R.personId = :personId")
})
public class Ride {

    private static final double MIN_COST = 10;
    /**
     * We want to have a cost of 10 Rupees per hour.
     */
    private static final double COST_MULTIPLIER = 10.0 / (60.0 * 60.0 * 1000.0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int startLocationId;

    @Column
    private int endLocationId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date startTime;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date endTime;

    @Column(nullable = false)
    private int cycleId;

    @Column(nullable = false)
    private int personId;

    @Column
    private double cost;

    public Ride() {}

    public Ride(int startLocationId, int endLocationId, Date startTime,
            Date endTime, int cycleId, int personId, double cost) {
        this.startLocationId = startLocationId;
        this.endLocationId   = endLocationId;
        this.startTime       = startTime;
        this.endTime         = endTime;
        this.cycleId         = cycleId;
        this.personId        = personId;
        this.cost            = cost;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public double getCost() { return cost;}

    public void setCost(double cost) { this.cost = cost;}

    public Date getStartTime() { return startTime;}

    public void setStartTime(Date startTime) { this.startTime = startTime;}

    public int getStartLocationId() { return startLocationId;}

    public void setStartLocationId(int startLocationId) {
        this.startLocationId = startLocationId;
    }

    public int getEndLocationId() { return endLocationId;}

    public void setEndLocationId(int endLocationId) {
        this.endLocationId = endLocationId;
    }

    public Date getEndTime() { return endTime;}

    public void setEndTime(Date endTime) { this.endTime = endTime;}

    public int getCycleId() { return cycleId;}

    public void setCycleId(int cycleId) { this.cycleId = cycleId;}

    public int getPersonId() { return personId;}

    public void setPersonId(int personId) { this.personId = personId;}

    public void calculateCost() {
        this.cost = Math.min(MIN_COST,
                COST_MULTIPLIER * (this.endTime.getTime()
                        - this.startTime.getTime())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startLocationId,
                endLocationId,
                startTime,
                endTime,
                cycleId,
                personId,
                cost
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return startLocationId == ride.startLocationId
                && endLocationId == ride.endLocationId
                && cycleId == ride.cycleId && personId == ride.personId
                && Double.compare(ride.cost, cost) == 0 && Objects.equal(startTime,
                ride.startTime
        ) && Objects.equal(
                endTime,
                ride.endTime
        );
    }

}
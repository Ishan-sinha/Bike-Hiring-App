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
@Table(name = "complaint")
@NamedQueries({
        @NamedQuery(name = "Complaint.findAll",
                query = "SELECT C FROM Complaint C")
})
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String details;

    @Column(nullable = false)
    private ComplaintStatus status;

    @Column(nullable = false)
    private int cycleId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date startTime;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date endTime;

    @Column(nullable = false)
    private int personId;

    public Complaint() {}

    public Complaint(String details, ComplaintStatus status, int cycleId,
            Date startTime, Date endTime, int personId) {
        this.details = details;
        this.status = status;
        this.cycleId = cycleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.personId = personId;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getDetails() { return details;}

    public void setDetails(String details) { this.details = details;}

    public ComplaintStatus getStatus() { return status;}

    public void setStatus(ComplaintStatus status) { this.status = status;}

    public Date getStartTime() { return startTime;}

    public void setStartTime(Date startTime) { this.startTime = startTime;}

    public Date getEndTime() { return endTime;}

    public void setEndTime(Date endTime) { this.endTime = endTime;}

    public int getCycleId() { return cycleId;}

    public void setCycleId(int cycleId) { this.cycleId = cycleId;}

    public int getPersonId() { return personId;}

    public void setPersonId(int personId) { this.personId = personId;}

    @Override
    public int hashCode() {
        return Objects.hashCode(details,
                status,
                cycleId,
                startTime,
                endTime,
                personId
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return cycleId == complaint.cycleId && personId == complaint.personId
                && Objects.equal(details, complaint.details)
                && status == complaint.status && Objects.equal(startTime,
                complaint.startTime
        ) && Objects.equal(
                endTime,
                complaint.endTime
        );
    }

    @Override
    public String toString() {
        return "Complaint{" + "id=" + id + ", details='" + details + '\''
                + ", status=" + status + ", cycleId=" + cycleId + ", startTime="
                + startTime + ", endTime=" + endTime + ", personId=" + personId
                + '}';
    }

}

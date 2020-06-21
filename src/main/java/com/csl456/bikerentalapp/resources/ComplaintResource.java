package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Complaint;
import com.csl456.bikerentalapp.core.ComplaintStatus;
import com.csl456.bikerentalapp.core.UserRole;
import com.csl456.bikerentalapp.db.ComplaintDAO;
import com.csl456.bikerentalapp.filter.LoggedIn;
import com.csl456.bikerentalapp.filter.RolesAllowed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("complaint")
@Produces(MediaType.APPLICATION_JSON)
public class ComplaintResource {

    private final ComplaintDAO complaintDAO;

    public ComplaintResource(ComplaintDAO complaintDAO) {
        this.complaintDAO = complaintDAO;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @LoggedIn
    public Complaint addComplaint(Complaint complaint) {
        complaint.setStartTime(new Date());
        complaint.setStatus(ComplaintStatus.UNRESOLVED);
        return complaintDAO.create(complaint);
    }

    @GET
    @UnitOfWork
    @RolesAllowed(UserRole.ADMIN)
    public List<Complaint> listComplaints() { return complaintDAO.findAll();}

    @DELETE
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{id}")
    @RolesAllowed(UserRole.ADMIN)
    public Complaint resolveComplaint(@PathParam("id") int complaintId) {
        Complaint complaint = complaintDAO
                .getById(complaintId)
                .orElseThrow(() -> new WebApplicationException(
                        "Complaint not found \"" + complaintId + "\"",
                        Response.Status.NOT_FOUND
                ));
        complaint.setStatus(ComplaintStatus.RESOLVED);
        complaint.setEndTime(new Date());
        return complaintDAO.create(complaint);
    }

}

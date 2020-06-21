package com.csl456.bikerentalapp.db;

import com.csl456.bikerentalapp.core.Complaint;
import com.csl456.bikerentalapp.core.ComplaintStatus;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ComplaintDAOTest {

    private final DAOTestExtension daoTestRule = DAOTestExtension
            .newBuilder()
            .addEntityClass(Complaint.class)
            .build();

    private ComplaintDAO complaintDAO;

    @Test
    public void createComplaint() {
        final Complaint complaint = daoTestRule.inTransaction(() -> complaintDAO
                .create(new Complaint("punctured",
                        ComplaintStatus.UNRESOLVED,
                        1,
                        new Date(123),
                        null,
                        1
                )));
        assertThat(complaint.getId()).isGreaterThan(0);
        assertThat(complaint.getDetails()).isEqualTo("punctured");
        assertThat(complaint.getStatus()).isEqualTo(ComplaintStatus.UNRESOLVED);
        assertThat(complaint.getPersonId()).isEqualTo(1);
        assertThat(complaint.getCycleId()).isEqualTo(1);
        assertThat(complaint.getStartTime()).isEqualTo(new Date(123));
    }

    @Test
    public void findAll() {
        daoTestRule.inTransaction(() -> {
            complaintDAO.create(new Complaint("punctured",
                    ComplaintStatus.UNRESOLVED,
                    1,
                    new Date(250),
                    null,
                    1
            ));
            complaintDAO.create(new Complaint("brake failed",
                    ComplaintStatus.RESOLVED,
                    2,
                    new Date(750),
                    new Date(1000),
                    2
            ));
        });
        final List<Complaint> complaints = complaintDAO.findAll();
        assertThat(complaints)
                .extracting("details")
                .containsOnly("punctured", "brake failed");
        assertThat(complaints).extracting("cycleId").containsOnly(1, 2);
        assertThat(complaints).extracting("personId").containsOnly(1, 2);
        assertThat(complaints)
                .extracting("status")
                .containsOnly(ComplaintStatus.UNRESOLVED,
                        ComplaintStatus.RESOLVED
                );
        assertThat(complaints)
                .extracting("startTime")
                .containsOnly(new Date(250), new Date(750));
        assertThat(complaints)
                .extracting("endTime")
                .containsOnly(null, new Date(1000));
    }

    @Test
    public void findById() {
        daoTestRule.inTransaction(() -> {
            complaintDAO.create(new Complaint("punctured",
                    ComplaintStatus.UNRESOLVED,
                    1,
                    new Date(0),
                    null,
                    1
            ));
        });
        final Complaint complaint = complaintDAO.getById(1).get();
        assertThat(complaint.getId()).isGreaterThan(0);
        assertThat(complaint.getDetails()).isEqualTo("punctured");
        assertThat(complaint.getStatus()).isEqualTo(ComplaintStatus.UNRESOLVED);
        assertThat(complaint.getPersonId()).isEqualTo(1);
        assertThat(complaint.getCycleId()).isEqualTo(1);
        assertThat(complaint.getStartTime()).isEqualTo(new Date(0));
    }

    @BeforeEach
    public void setUp() {
        complaintDAO = new ComplaintDAO(daoTestRule.getSessionFactory());
    }

}

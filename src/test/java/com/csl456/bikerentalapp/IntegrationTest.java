package com.csl456.bikerentalapp;

import com.csl456.bikerentalapp.core.Person;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
public class IntegrationTest {

    private static final String TMP_FILE = createTempFile();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath(
            "test.yml");

    private static final DropwizardAppExtension<BikeRentalAppConfiguration> RULE
            = new DropwizardAppExtension<>(BikeRentalApplication.class,
            CONFIG_PATH,
            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE)
    );

    @BeforeAll
    private static void migrateDb() throws Exception {
        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("test", null).getAbsolutePath();
        } catch (IOException e) { throw new IllegalStateException(e);}
    }

    @Test
    public void testPostPerson() {
        final Person person = new Person("Aditya Gupta",
                1234567890L,
                "aditya@example.com"
        );
        final Person newPerson = postPerson(person);
        assertThat(newPerson.getId()).isNotNull();
        assertThat(newPerson.getName()).isEqualTo(person.getName());
        assertThat(newPerson.getContactNumber()).isEqualTo(person.getContactNumber());
        assertThat(newPerson.getEmail()).isEqualTo(person.getEmail());
    }

    private Person postPerson(Person person) {
        return RULE
                .client()
                .target("http://localhost:" + RULE.getLocalPort() + "/person")
                .request()
                .post(Entity.entity(person, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Person.class);
    }

}
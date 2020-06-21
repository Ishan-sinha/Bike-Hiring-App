package com.csl456.bikerentalapp;

import com.csl456.bikerentalapp.core.SMTPServerDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BikeRentalAppConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private final DataSourceFactory database = new DataSourceFactory();

    @NotNull
    @JsonProperty("smtpServerDetails")
    private SMTPServerDetails smtpServerDetails;

    public SMTPServerDetails getSmtpServerDetails() { return smtpServerDetails;}

    public void setSmtpServerDetails(SMTPServerDetails smtpServerDetails) {
        this.smtpServerDetails = smtpServerDetails;
    }

    public DataSourceFactory getDatabase() { return database;}

}

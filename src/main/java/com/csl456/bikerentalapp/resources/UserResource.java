package com.csl456.bikerentalapp.resources;

import com.csl456.bikerentalapp.core.Person;
import com.csl456.bikerentalapp.core.SMTPServerDetails;
import com.csl456.bikerentalapp.core.User;
import com.csl456.bikerentalapp.core.UserRole;
import com.csl456.bikerentalapp.db.PersonDAO;
import com.csl456.bikerentalapp.db.SessionDAO;
import com.csl456.bikerentalapp.db.UserDAO;
import com.csl456.bikerentalapp.filter.LoggedIn;
import com.csl456.bikerentalapp.filter.RolesAllowed;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.dropwizard.hibernate.UnitOfWork;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import javax.annotation.Nonnull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Random random = new Random();
    private static final LoadingCache<String, String> otpCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(
                        @Nonnull String username) { return generateOTP();}
            });
    private static final Cache<String, User> userCache = CacheBuilder
            .newBuilder()
            .build();
    private final UserDAO           userDAO;
    private final SessionDAO        sessionDAO;
    private final PersonDAO         personDAO;
    private final SMTPServerDetails smtpServerDetails;

    public UserResource(UserDAO userDAO, SessionDAO sessionDAO,
            PersonDAO personDAO, SMTPServerDetails smtpServerDetails) {
        this.userDAO           = userDAO;
        this.sessionDAO        = sessionDAO;
        this.personDAO         = personDAO;
        this.smtpServerDetails = smtpServerDetails;
    }

    private static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 8; i++) otp.append(random.nextInt(10));
        return otp.toString();
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(User user) throws ExecutionException {
        Person person = personDAO
                .findById(user.getPersonId())
                .orElseThrow(() -> new WebApplicationException(
                        "Related Person not Found",
                        Response.Status.NOT_FOUND));
        String otp = otpCache.get(user.getUsername());
        Email email = EmailBuilder
                .startingBlank()
                .from("Bike Rental Admin", "noreply@bikerentalapp.com")
                .to(person.getName(), person.getEmail())
                .withSubject("Your Registration OTP")
                .withPlainText("Your OTP is " + otp)
                .buildEmail();
        MailerBuilder.withSMTPServer(
                smtpServerDetails.getHost(),
                smtpServerDetails.getPort(),
                smtpServerDetails.getUsername(),
                smtpServerDetails.getPassword()
        ).buildMailer().sendMail(email);
        userCache.put(user.getUsername(), user);
    }

    @GET
    @UnitOfWork
    @RolesAllowed(UserRole.ADMIN)
    public List<User> listUsers() { return userDAO.findAll();}

    @POST
    @Path("changePass")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @LoggedIn
    public void changePassword(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("newPassword") String newPassword) throws Exception {
        if (userDAO.findUsersByUsernameAndPassword(username, password)
                == null) { throw new Exception("Username or Password Wrong"); }
        sessionDAO.removeAll(username);
        userDAO.changePassword(username, newPassword);
    }

    @POST
    @Path("forgotPass/{username}")
    @UnitOfWork
    public void forgotPass(@PathParam("username") String username) throws
            Exception {
        User user = userDAO.findByUserName(username);
        Person person = personDAO
                .findById(user.getPersonId())
                .orElseThrow(() -> new WebApplicationException(
                        "Related Person not Found",
                        Response.Status.NOT_FOUND));
        String otp = otpCache.get(username);
        Email email = EmailBuilder
                .startingBlank()
                .from("Bike Rental Admin", "noreply@bikerentalapp.com")
                .to(person.getName(), person.getEmail())
                .withSubject("Your Forgot Password OTP")
                .withPlainText("Your OTP is " + otp)
                .buildEmail();
        MailerBuilder.withSMTPServer(
                smtpServerDetails.getHost(),
                smtpServerDetails.getPort(),
                smtpServerDetails.getUsername(),
                smtpServerDetails.getPassword()
        ).buildMailer().sendMail(email);
    }

    @POST
    @Path("validateRegistrationOTP/{username}")
    @UnitOfWork
    public User validateRegistrationOTP(@PathParam("username") String username,
            @FormParam("otp") String otp) throws ExecutionException {
        if (otpCache.get(username).equals(otp)) {
            return userDAO.create(userCache.getIfPresent(username));
        } else { throw new WebApplicationException("OTP does not match",
                Response.Status.UNAUTHORIZED); }
    }

    @POST
    @Path("validateForgotPassOTP/{username}")
    @UnitOfWork
    public User validateForgotPassOTP(@PathParam("username") String username,
            @FormParam("otp") String otp) throws ExecutionException {
        if (otpCache.get(username).equals(otp)) {
            return userDAO.findByUserName(username);
        } else { throw new WebApplicationException("OTP does not match",
                Response.Status.UNAUTHORIZED); }
    }

}

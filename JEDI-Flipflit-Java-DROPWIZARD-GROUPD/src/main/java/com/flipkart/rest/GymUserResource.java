package com.flipkart.rest;

import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.GymUser;
import com.flipkart.business.GymUserService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class GymUserResource {

    private final GymUserService userService = new GymUserService();

    @POST
    @Path("/register/customer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCustomer(GymCustomer customer) {
        boolean isRegistered = userService.registerCustomer(customer);
        if (isRegistered) {
            return Response.status(Response.Status.CREATED).entity("Customer registered successfully!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Customer registration failed.").build();
        }
    }

    @POST
    @Path("/register/gym-owner")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerGymOwner(GymOwner gymOwner) {
        gymOwner.setVerified(false); // New owners are unverified by default
        boolean isRegistered = userService.registerGymOwner(gymOwner);
        if (isRegistered) {
            return Response.status(Response.Status.CREATED).entity("Gym Owner registered successfully! Awaiting admin approval.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Gym Owner registration failed.").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(GymUser user) {
        boolean isAuthenticated = userService.authenticateUser(user);
        if (isAuthenticated) {
            return Response.ok("Login successful! Welcome " + user.getEmail()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed. Please check credentials.").build();
        }
    }
}


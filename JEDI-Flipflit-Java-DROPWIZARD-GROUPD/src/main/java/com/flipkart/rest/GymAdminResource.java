package com.flipkart.rest;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;
import com.flipkart.business.GymAdminService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class GymAdminResource {

    private final GymAdminService adminService = new GymAdminService();

    @GET
    @Path("/gym-owners")
    public Response getAllGymOwners() {
        List<GymOwner> owners = adminService.getGymOwners();
        return Response.ok(owners).build();
    }

    @GET
    @Path("/gym-centres")
    public Response getAllGymCentres() {
        List<GymCentre> centres = adminService.getGym();
        return Response.ok(centres).build();
    }

    @GET
    @Path("/pending-owners")
    public Response getPendingGymOwnerRequests() {
        List<GymOwner> pendingOwners = adminService.viewAllPendingGymOwnerRequests();
        return Response.ok(pendingOwners).build();
    }

    @GET
    @Path("/pending-gyms")
    public Response getPendingGymRequests() {
        List<GymCentre> pendingGyms = adminService.viewAllPendingGymRequests();
        return Response.ok(pendingGyms).build();
    }

    @PUT
    @Path("/approve/owner/{email}")
    public Response approveSingleOwner(@PathParam("email") String email) {
        adminService.approveSingleGymOwnerRequest(email);
        return Response.ok("Gym Owner approved: " + email).build();
    }

    @PUT
    @Path("/approve/gym/{gymId}")
    public Response approveSingleGym(@PathParam("gymId") String gymId) {
        adminService.approveSingleGymRequest(gymId);
        return Response.ok("Gym Centre approved: " + gymId).build();
    }
}

package com.flipkart.rest;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.Slot;
import com.flipkart.business.GymOwnerService;
import com.flipkart.utils.IdGenerator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/owner")
@Produces(MediaType.APPLICATION_JSON)
public class GymOwnerResource {

    private final GymOwnerService ownerService = new GymOwnerService();

    @POST
    @Path("/gyms/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGym(GymCentre gym) {
        gym.setGymId(IdGenerator.generateId("Gym"));
        gym.setVerified(false); // New gyms are unverified by default
        boolean isAdded = ownerService.addGym(gym);
        if (isAdded) {
            return Response.status(Response.Status.CREATED)
                    .entity("Gym added successfully! Awaiting admin approval. Gym ID: " + gym.getGymId())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add gym. Ensure you are an approved owner.").build();
    }

    @GET
    @Path("/gyms/{ownerEmail}")
    public Response getMyGyms(@PathParam("ownerEmail") String ownerEmail) {
        List<GymCentre> gyms = ownerService.getGymDetail(ownerEmail);
        return Response.ok(gyms).build();
    }

    @POST
    @Path("/slots/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSlot(Slot slot) {
        slot.setSlotId(IdGenerator.generateId("Slot"));
        // The service layer will check if the gym is approved.
        // We need to know the owner's email to check if the owner is approved.
        // This is a limitation of the current design. For now, we assume the client knows.
        ownerService.addSlot(slot); // This method needs refactoring to pass owner email
        return Response.status(Response.Status.CREATED).entity("Slot added successfully! Slot ID: " + slot.getSlotId()).build();
    }

    @GET
    @Path("/slots/{gymId}")
    public Response getGymSlots(@PathParam("gymId") String gymId) {
        List<Slot> slots = ownerService.getSlotsForGym(gymId);
        return Response.ok(slots).build();
    }
}

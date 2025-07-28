package com.flipkart.rest;

import com.flipkart.bean.Booking;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.Payment;
import com.flipkart.business.GymCustomerService;
import com.flipkart.utils.IdGenerator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class GymCustomerResource {

    private final GymCustomerService customerService = new GymCustomerService();

    @GET
    @Path("/gyms/address/{address}")
    public Response getGymsByAddress(@PathParam("address") String address) {
        List<GymCentre> gyms = customerService.getGymInCity(address);
        return Response.ok(gyms).build();
    }

    @GET
    @Path("/gyms/date/{dateStr}")
    public Response getGymsByDate(@PathParam("dateStr") String dateStr) {
        List<GymCentre> gyms = customerService.getGymsByDate(dateStr);
        return Response.ok(gyms).build();
    }

    @POST
    @Path("/bookings/book")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bookSlot(Booking bookingRequest) {
        String bookingId = customerService.bookSlot(
                bookingRequest.getGymId(),
                bookingRequest.getSlotId(),
                bookingRequest.getCustomerEmail(),
                bookingRequest.getDate()
        );

        if (bookingId != null && !bookingId.equals("Slot not available")) {
            // In a real app, you'd handle payment here.
            // For now, we simulate a successful payment.
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setTransactionId(IdGenerator.generateId("Trans"));
            payment.setPaymentStatus("Success");
            customerService.makePayment(payment);

            return Response.status(Response.Status.CREATED)
                    .entity("Booking successful! Booking ID: " + bookingId)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Booking failed: " + bookingId).build();
    }

    @GET
    @Path("/bookings/{email}")
    public Response getMyBookings(@PathParam("email") String email) {
        List<Booking> bookings = customerService.getBookings(email);
        return Response.ok(bookings).build();
    }

    @DELETE
    @Path("/bookings/cancel/{bookingId}")
    public Response cancelBooking(@PathParam("bookingId") String bookingId) {
        boolean isCancelled = customerService.cancelBooking(bookingId, null); // Email not needed for this logic
        if (isCancelled) {
            return Response.ok("Booking " + bookingId + " cancelled successfully.").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Booking cancellation failed.").build();
    }
}
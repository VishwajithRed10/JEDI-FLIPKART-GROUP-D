package com.flipkart.business;

import com.flipkart.bean.*;
import java.util.*;
/*
 *@Author : "Vishwajith"
 *@ClassName: "GymCustomerServiceInterface"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCustomer, com.flipkart.bean.Booking, com.flipkart.bean.GymCentre, com.flipkart.bean.Slot, com.flipkart.bean.Payment"
 */
public interface GymCustomerServiceInterface {
    public GymCustomer getProfile(GymCustomer customer);


    public void editProfile(GymCustomer customer);

    public List<Booking> getBookings(String email);

    public boolean cancelBooking(String bookingId, String email);


    public List<GymCentre> getGymInCity(String city);


    public List<Slot> getSlotInGym(String gymId);


    public String bookSlot(String gymId, String slotId, String email, Date date);


    public boolean isSlotBooked(String slotId, String gymId);


    public boolean hasBookedSlotAlready(String slotId, String customerEmail, Date date);

    public List<GymCentre> getGymsByDate(String dateStr);

    public void makePayment(Payment payment);

}

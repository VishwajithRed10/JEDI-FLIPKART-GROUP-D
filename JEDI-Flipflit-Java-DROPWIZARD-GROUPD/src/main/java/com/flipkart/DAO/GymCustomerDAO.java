package com.flipkart.DAO;

import java.util.Date;
import java.util.List;

import com.flipkart.bean.*;
import com.flipkart.exception.NoSlotsFoundException;
/*
 *@Author : "Vishwajith"
 *@ClassName: "GymCustomerDAO"
 *@Exceptions: "com.flipkart.exception.NoSlotsFoundException"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCentre, com.flipkart.bean.Slot, com.flipkart.bean.Booking, com.flipkart.bean.Payment, java.util.Date"
 */
public interface GymCustomerDAO {
    public List<GymCentre> fetchGymList();

    public List<Slot> fetchSlotsByGym(String gymId) throws NoSlotsFoundException;

    public List<Booking> fetchBookedSlots(String email);

    public String bookSlots(String bookingId, String slotId, String gymId, String type, Date date, String customerEmail);

    public boolean isFull(String slotId, String date);

    public boolean alreadyBooked(String slotId, String email, String date);

    public boolean cancelBookingById(String bookingId);

    public boolean checkSlotExists(String slotId, String gymId);

    public boolean checkGymApprove(String gymId);

    public void editProfile(GymCustomer customer);

    public List<GymCentre> fetchGymsByDate(Date date);

    public void processPayment(Payment payment);
}

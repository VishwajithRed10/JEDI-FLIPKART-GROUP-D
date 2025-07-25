package com.flipkart.DAO;

import java.util.Date;
import java.util.List;

import com.flipkart.bean.Booking;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.Slot;
import com.flipkart.exception.NoSlotsFoundException;

public interface GymCustomerDAO {
	public List<GymCentre> fetchGymList();

	public List<Slot> fetchSlotsByGym(String gymId) throws NoSlotsFoundException;

	public List<Booking> fetchBookedSlots(String email);

	public void bookSlots(String bookingId, String slotId, String gymId, String type, Date date, String customerEmail);

	public boolean isFull(String slotId, String date);

	public boolean alreadyBooked(String slotId, String email, String date);

	public boolean cancelBookingById(String bookingId);

	public boolean checkSlotExists(String slotId, String gymId);

	public boolean checkGymApprove(String gymId);

	public void editProfile(GymCustomer customer);
}

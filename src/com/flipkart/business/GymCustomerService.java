/**
 *
 */
package com.flipkart.business;

import com.flipkart.DAO.GymCustomerDAO;
import com.flipkart.DAO.GymCustomerDAOImpl;
import com.flipkart.bean.*;
import com.flipkart.exception.NoSlotsFoundException;
import com.flipkart.utils.IdGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/*
 *@Author : "Ronil Patil"
 *@ClassName: "GymCustomerService"
 *@Exceptions: "com.flipkart.exception.NoSlotsFoundException, java.text.ParseException"
 *@Version : "1.0"
 *@See : "com.flipkart.DAO.GymCustomerDAOImpl, com.flipkart.bean.GymCustomer, com.flipkart.bean.Booking, com.flipkart.bean.Slot, com.flipkart.bean.GymCentre, com.flipkart.bean.Payment"
 */
public class GymCustomerService implements GymCustomerServiceInterface {

	List<GymCustomer> customers = new ArrayList<>();
	List<Booking> bookings = new ArrayList<>();

	List<Slot> slots = new ArrayList<>();
	List<GymCentre> gyms = new ArrayList<>();



	GymCustomerDAO customerDAO = new GymCustomerDAOImpl();

	public GymCustomer getProfile(GymCustomer customer) {
		for (GymCustomer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				return cust;
		}
		return null;
	}




	public void editProfile(GymCustomer customer) {
		customerDAO.editProfile(customer);
	}



	public List<Booking> getBookings(String email) {
		return customerDAO.fetchBookedSlots(email);
	}


	public boolean cancelBooking(String bookingId, String email) {
		// Here you could add logic to ensure a customer only cancels their own booking.
		// For now, we will just call the DAO.
		return customerDAO.cancelBookingById(bookingId);
	}

	public List<GymCentre> getGymInCity(String city) {
		// Fetch all approved gyms from the database
		List<GymCentre> allGyms = customerDAO.fetchGymList();
		List<GymCentre> gymsInCity = new ArrayList<>();

		// Filter the list for the specified city
		for (GymCentre gym : allGyms) {
			if (gym.getAddress().equalsIgnoreCase(city) && gym.isVerified()) {
				gymsInCity.add(gym);
			}
		}
		return gymsInCity;
	}



	public List<Slot> getSlotInGym(String gymId) {
        try {
            return customerDAO.fetchSlotsByGym(gymId);
        } catch (NoSlotsFoundException e) {
            throw new RuntimeException(e);
        }
    }


	public String bookSlot(String gymId, String slotId, String email, Date date) {

		boolean slotExists = false;
		List<Slot> availableSlots = getSlotInGym(gymId);
		for (Slot s : availableSlots) {
			if (s.getSlotId().equals(slotId)) {
				slotExists = true;
				break;
			}
		}

		if (!slotExists) {

			return "Slot not available"; // "Slot not found"
		}

		String bookingId = IdGenerator.generateId("Booking");
		customerDAO.bookSlots(bookingId, slotId, gymId, "Confirmed", date, email);
		return bookingId; // "Successfully booked"
	}




	/**
	 * Checks if the slot is already booked or not
	 * @param slotId the slot id for which the booking status is requested
	 * @param date the date on which the booking status is requested
	 * @return returns true if the slot id for the given date is fully booked else returns false
	 */
//	public boolean isSlotBooked(String slotId, Date date) {
//		for (Slot s : slots) {
//			if (s.getSlotId().equals(slotId)) {
//				if (s.getNumOfSeats() <= s.getNumOfSeatsBooked())
//					return true;
//				else
//					return false;
//			}
//		}
//		return false;
//	}

	public boolean isSlotBooked(String slotId, String gymId) {
        List<Slot> slots = null;
        try {
            slots = customerDAO.fetchSlotsByGym(gymId);
        } catch (NoSlotsFoundException e) {
            throw new RuntimeException(e);
        }
        for (Slot s : slots) {
			if (s.getSlotId().equals(slotId)) {
				// Returns true if there are available seats
				return s.getNumOfSeatsBooked() < s.getNumOfSeats();
			}
		}
		return false; // Slot not found in that gym
	}
	/**
	 * Checks if the customer has already booked a seat in the same slot for the given date
	 * @param slotId the slot id for which the booking status is requested
	 * @param date the date on which the booking status is requested
	 * @param customerEmail the email of customer for which the booking status is getting checked
	 * @return returns true if the customer has already booked a seat on the same date in the same slot
	 */
	public boolean hasBookedSlotAlready(String slotId, String customerEmail, Date date) {
		for (Booking b : bookings) {
			if (b.getSlotId().equals(slotId)) {
				if (b.getCustomerEmail().equals(customerEmail))
					return true;
			}
		}
		return false;
	}

	public List<GymCentre> getGymsByDate(String dateStr) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateStr);
			return customerDAO.fetchGymsByDate(date);
		} catch (ParseException e) {
			System.out.println("Invalid date format. Please use yyyy-MM-dd.");
			return new ArrayList<>(); // Return empty list on error
		}
	}

	public void makePayment(Payment payment) {
		customerDAO.processPayment(payment);
	}


}

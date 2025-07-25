/**
 *
 */
package com.flipkart.business;

import com.flipkart.DAO.GymCustomerDAO;
import com.flipkart.DAO.GymCustomerDAOImpl;
import com.flipkart.bean.*;
import com.flipkart.exception.NoSlotsFoundException;
import com.flipkart.utils.IdGenerator;

import java.util.Date;
import java.util.*;

public class GymCustomerService implements GymCustomerServiceInterface {

	List<GymCustomer> customers = new ArrayList<>();
	List<Booking> bookings = new ArrayList<>();

	List<Slot> slots = new ArrayList<>();
	List<GymCentre> gyms = new ArrayList<>();
//
//	Date d1 = new Date();
//	Customer customer1 = new Customer("c1@gmail.com", "c1", "Customer", "Vaishnavi", "0000", 22, "Kanpur");
//	Customer customer2 = new Customer("c2@gmail.com", "c2", "Customer", "Anjali", "0000", 32, "Vadodara");
//	Customer customer3 = new Customer("c3@gmail.com", "c3", "Customer", "Sudha", "0000", 42, "Kolkata");
//	Customer customer4 = new Customer("c4@gmail.com", "c4", "Customer", "Aaishu", "0000", 52, "Mumbai");
//
//
//	Booking b1 = new Booking("123", "121", "171", "confirmed", d1, "c1@gmail.com", "John");
//	Booking b2 = new Booking("173", "191", "131", "waitlisted", d1, "c2@gmail.com", "Jack");
//	Booking b3 = new Booking("113", "129", "173", "confirmed", d1, "c3@gmail.com", "Johnathon");
//	Booking b4 = new Booking("193", "127", "971", "waitlisted", d1, "c4@gmail.com", "J");
//
//	Slot s1 = new Slot("900", "1400", "1500", 100, "John", "g1");
//	Slot s2 = new Slot("910", "1500", "1600", 100, "J", "g2");
//	Slot s3 = new Slot("930", "1600", "1700", 100, "Jack", "g3");
//	Slot s4 = new Slot("950", "1700", "1800", 100, "Johnny", "g4");
//
//
//	Gym gym1 = new Gym("g1", "gym1", "gymowner1@gmail.com", "Kanpur", 2, 5, true);
//	Gym gym2 = new Gym("g2", "gym2", "gymowner2@gmail.com", "Hyderabad", 3, 5, true);
//	Gym gym3 = new Gym("g3", "gym3", "gymowner3@gmail.com", "Bangalore", 2, 3, true);
//	Gym gym4 = new Gym("g4", "gym4", "gymowner4@gmail.com", "Cochin", 6, 5, true);
//
//	public CustomerBusiness() {
//		customers.add(customer1);
//		customers.add(customer2);
//		customers.add(customer3);
//		customers.add(customer4);
//
//		bookings.add(b1);
//		bookings.add(b2);
//		bookings.add(b3);
//		bookings.add(b4);
//
//		slots.add(s1);
//		slots.add(s2);
//		slots.add(s3);
//		slots.add(s4);
//
//		gyms.add(gym1);
//		gyms.add(gym2);
//		gyms.add(gym3);
//		gyms.add(gym4);
//	}

	/**
	 * Obtains customer's profile details 
	 * @param customer the Customer object for which the profile details are requested
	 * @return Customer the Customer's object
	 */

	GymCustomerDAO customerDAO = new GymCustomerDAOImpl();

	public GymCustomer getProfile(GymCustomer customer) {
		for (GymCustomer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				return cust;
		}
		return null;
	}

	/**
	 * Gives functionality of updating customer's personal data. 
	 * @param customer the Customer object for which the profile data needs to be updated
	 */
//	public void editProfile(GymCustomer customer) {
//		for (GymCustomer cust : customers) {
//			if (cust.getEmail().equals(customer.getEmail())) {
//				cust.setName(customer.getName());
//				cust.setPhoneNumber(customer.getPhoneNumber());
//				cust.setAge(customer.getAge());
//				cust.setAddress(customer.getAddress());
//				customers.add(cust);
//				System.out.println(ColorConstants.GREEN+"Successfully edited your profile\ns"+ColorConstants.RESET);
//				break;
//			}
//		}
//	}

	public void editProfile(GymCustomer customer) {
		customerDAO.editProfile(customer);
	}
	/**
	 * Obtains all the bookings done by the given customer email.
	 * @param email the Customer email for which the bookings data are requested
	 * @return List of bookings done by the given customer email
	 */
//	public List<Booking> getBookings(String email) {
//
//		List<Booking> customerBookings = new ArrayList<Booking>();
//
//		for (Booking b : bookings) {
//			if (b.getCustomerEmail().equals(email)) {
//				customerBookings.add(b);
//			}
//		}
//		return customerBookings;
//	}

	public List<Booking> getBookings(String email) {
		return customerDAO.fetchBookedSlots(email);
	}
	/**
	 * Performs booking cancellation operation for the given customer email.
	 * @param bookingId the id of booking for which cancellation needs to be performed
	 * @param email the Customer email for which the booking cancellation is requested
	 * @return returns true of the booking gets cancelled successfully else returns false
	 */
//	public boolean cancelBooking(String bookingId, String email) {
//
//		for (Booking booking : bookings) {
//			if (booking.getBookingId().equals(bookingId)) {
//				bookings.remove(booking);
//				System.out.println(ColorConstants.GREEN + "Successfully cancelled your booking" + ColorConstants.RESET);
//				return true;
//			}
//		}
//		return false;
//	}
	public boolean cancelBooking(String bookingId, String email) {
		// Here you could add logic to ensure a customer only cancels their own booking.
		// For now, we will just call the DAO.
		return customerDAO.cancelBookingById(bookingId);
	}
	/**
	 * Obtains all the gyms for the given city.
	 * @param city the city name for which the gym list is requested
	 * @return returns List of gyms available for the given city
	 */
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
	/**
	 * Obtains all the slots for the given gymId.
	 * @param gymId the Gym Id for which the slot details are requested
	 * @return returns List of available slots for the given gymId
	 */
//	public List<Slot> getSlotInGym(String gymId) {
//		List<Slot> slotsOfGym = new ArrayList<>();
//		for (Slot s : slots) {
//			if (s.getGymId().equals(gymId)) {
//				slotsOfGym.add(s);
//			}
//		}
//		return slotsOfGym;
//	}

	public List<Slot> getSlotInGym(String gymId) {
        try {
            return customerDAO.fetchSlotsByGym(gymId);
        } catch (NoSlotsFoundException e) {
            throw new RuntimeException(e);
        }
    }
	/**
	 * Performs booking operation for the given customer email on the given date for the given slotId
	 * @param email the email of customer who requested the booking operation
	 * @param slotId the slot id in which the customer wants to book a seat
	 * @param date the date on which the customer wants to book a seat
	 * @return returns integer signal based on the customer's booking status
	 */
//	public int bookSlot(String gymId, String slotId, String email, Date date) {
//		List<Booking> tempBookings = getBookings(email);
//		boolean flag=false;
//		for(Booking booking:bookings)
//		{
//			if(booking.getCustomerEmail().equals(email) && booking.getType().equals("confirmed"))
//			{
//				flag=true;
//				tempBookings.add(booking);
//			}
//		}
//		if(flag)
//		{
//			boolean isDate=false;
//			for(Booking booking:tempBookings)
//			{
//				if(booking.getDate().equals(date))
//				{
//					isDate=true;
//					for(Slot slot:slots)
//					{
//						if(slot.getSlotId().equals(slotId) && !slot.getGymId().equals(gymId))
//						{
//							int num=slot.getNumOfSeatsBooked();
//							if(!isSlotBooked(slotId,date))
//							{
//								slot.setNumOfSeatsBooked(num--);
//								Booking getBooking = new Booking();
//								getBooking.setBookingId(IdGenerator.generateId("Booking"));
//								Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"confirmed",date,email,slot.getTrainer());
//								bookings.add(tempBooking);
//								bookings.remove(booking);
//								return 0;
//							}
//							else
//							{
//								slot.setNumOfSeatsBooked(num--);
//								Booking getBooking = new Booking();
//								getBooking.setBookingId(IdGenerator.generateId("Booking"));
//								Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"waitlisted",date,email,slot.getTrainer());
//								bookings.add(tempBooking);
//								return 1;
//							}
//						}
//					}
//					return 3;
//				}
//			}
//			if(!isDate)
//			{
//				for(Slot slot:slots)
//				{
//					if(slot.getSlotId().equals(slotId) && slot.getGymId().equals(gymId))
//					{
//						int num=slot.getNumOfSeatsBooked();
//						slot.setNumOfSeatsBooked(num--);
//						Booking getBooking = new Booking();
//						getBooking.setBookingId(IdGenerator.generateId("Booking"));
//						if(!isSlotBooked(slotId,date))
//						{
//							Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"confirmed",date,email,slot.getTrainer());
//							bookings.add(tempBooking);
//							return 2;
//						}
//						else
//						{
//							Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"waitlisted",date,email,slot.getTrainer());
//							bookings.add(tempBooking);
//							return 1;
//						}
//					}
//				}
//			}
//		}
//		else
//		{
//			for(Slot slot:slots)
//			{
//				if(slot.getSlotId().equals(slotId) && slot.getGymId().equals(gymId))
//				{
//					int num=slot.getNumOfSeatsBooked();
//					slot.setNumOfSeatsBooked(num--);
//					Booking getBooking = new Booking();
//					getBooking.setBookingId(IdGenerator.generateId("Booking"));
//					if(!isSlotBooked(slotId,date))
//					{
//						Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"confirmed",date,email,slot.getTrainer());
//						bookings.add(tempBooking);
//						return 2;
//					}
//					else
//					{
//						Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"waitlisted",date,email,slot.getTrainer());
//						bookings.add(tempBooking);
//						return 1;
//					}
//				}
//			}
//			return 3;
//		}
//		return 1;
//	}

	public int bookSlot(String gymId, String slotId, String email, Date date) {
		// First, check if the slot exists for the given gym
		boolean slotExists = false;
		List<Slot> availableSlots = getSlotInGym(gymId);
		for (Slot s : availableSlots) {
			if (s.getSlotId().equals(slotId)) {
				slotExists = true;
				break;
			}
		}

		if (!slotExists) {
			return 3; // "Slot not found"
		}

		// Proceed with booking logic (this part may need further DAO integration)
		// For now, let's assume a simple booking
		String bookingId = IdGenerator.generateId("Booking");
		customerDAO.bookSlots(bookingId, slotId, gymId, "Confirmed", date, email);
		return 2; // "Successfully booked"
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


}

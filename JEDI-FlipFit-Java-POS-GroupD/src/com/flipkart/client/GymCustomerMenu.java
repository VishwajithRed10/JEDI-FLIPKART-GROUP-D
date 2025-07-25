package com.flipkart.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.flipkart.bean.Booking;
import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.Slot;
import com.flipkart.business.GymCustomerService;
import com.flipkart.business.GymUserService;

public class GymCustomerMenu {

	GymCustomer customer = new GymCustomer();
	GymCustomerService customerBusiness = new GymCustomerService();
	Scanner sc = new Scanner(System.in);

	public void registerCustomer() {
		System.out.print("Enter email: ");
		customer.setEmail(sc.next());
		System.out.print("Enter password: ");
		customer.setPassword(sc.next());
		System.out.print("Enter Name: ");
		customer.setName(sc.next());
		System.out.print("Enter Phone Number: ");
		customer.setPhoneNumber(sc.next());
		System.out.print("Enter Age: ");
		customer.setAge(Integer.valueOf(sc.next()));
		System.out.print("Enter Address: ");
		customer.setAddress(sc.next());
		GymUserService userBusiness = new GymUserService();
		userBusiness.registerCustomer(customer);

		System.out.println("Customer registered successfully!");

	}

	public void viewGyms(String email) throws ParseException {
		getGyms();
		System.out.print("Enter gym ID: ");
		String gymId = sc.next();
		System.out.print("Enter Date (yyyy-mm-dd): ");
		String dateStr = sc.next();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dateStr);

		List<Slot> slots = customerBusiness.getSlotInGym(gymId);
		for (Slot slot : slots) {
			System.out.print("Slot Id: " + slot.getSlotId());
//			System.out.print("Availability: " + customerBusiness.isSlotBooked(slot.getSlotId(), date));
			System.out.print(" Availability: " + customerBusiness.isSlotBooked(slot.getSlotId(), gymId));
		}
		System.out.print("Enter the slot ID which you want to book: ");
		String slotId = sc.next();
		int bookingResponse = customerBusiness.bookSlot(gymId,slotId, email, date);
		switch (bookingResponse) {
		case 0:
			System.out.println("You have already booked this time. Cancelling the previous one and booking this slot");
			break;
		case 1:
			System.out.println("Slot is already booked, added to the waiting list");
			break;
		case 2:
			System.out.println("Successfully booked the slot");
			break;
		case 3:
			System.out.println("Slot not found");
			break;
		default:
			System.out.println("Booking failed");
		}
	}

//	public void editProfile(String email) {
//		System.out.print("Enter password: ");
//		customer.setPassword(sc.next());
//		System.out.print("Enter Name: ");
//		customer.setName(sc.next());
//		System.out.print("Enter Phone Number: ");
//		customer.setPhoneNumber(sc.next());
//		System.out.print("Enter Age: ");
//		customer.setAge(Integer.valueOf(sc.next()));
//		System.out.print("Enter Address: ");
//		customer.setAddress(sc.next());
//		System.out.println("Successfully edited your profile");
//	}
public void editProfile(String email) {
	GymCustomer customerToUpdate = new GymCustomer();
	customerToUpdate.setEmail(email); // Set the email of the logged-in user

	System.out.print("Enter New Password: ");
	customerToUpdate.setPassword(sc.next());
	System.out.print("Enter New Name: ");
	customerToUpdate.setName(sc.next());
	System.out.print("Enter New Phone Number: ");
	customerToUpdate.setPhoneNumber(sc.next());
	System.out.print("Enter New Age: ");
	customerToUpdate.setAge(sc.nextInt());
	System.out.print("Enter New Address: ");
	sc.nextLine(); // Consume the leftover newline
	customerToUpdate.setAddress(sc.nextLine());

	customerBusiness.editProfile(customerToUpdate);
	System.out.println("Successfully edited your profile.");
}

	public void getGyms() {
		System.out.print("Enter your address: ");
		List<GymCentre> gyms = customerBusiness.getGymInCity(sc.next());
		for (GymCentre gym : gyms) {
			System.out.print("Gym Id: " + gym.getGymId());
			System.out.print("Gym Owner Email: " + gym.getOwnerEmail());
			System.out.print("Gym Name: " + gym.getGymName());
			System.out.println();
		}
	}

	public void cancelBooking(String email) {
		System.out.print("Enter booking ID that you want to cancel: ");
		String bookingId = sc.next();

		// Capture the boolean result from the business layer
		boolean isCancelled = customerBusiness.cancelBooking(bookingId, email);

		if (isCancelled) {
			System.out.println("Booking with ID " + bookingId + " has been cancelled successfully.");
		} else {
			System.out.println("Failed to cancel booking. Please check the Booking ID and try again.");
		}
	}

	public void customerMenu(String email) throws ParseException {
		int choice = 0;

		while (choice != 5) {
			System.out.println("Menu:-");
			System.out.println("1.Search Gyms \n2.View Booked Slots \n3.Cancel Booked Slots \n4.Edit Profile \n5.Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				viewGyms(email);
				break;
				case 2:
					List<Booking> bookings = customerBusiness.getBookings(email);
					if (bookings.isEmpty()) {
						System.out.println("You have no booked slots.");
					} else {
						System.out.println("Your Booked Slots:");
						for (Booking booking : bookings) {
							System.out.println("  Booking ID: " + booking.getBookingId() + ", Gym ID: " + booking.getGymId() + ", Slot ID: " + booking.getSlotId() + ", Date: " + booking.getDate());
						}
					}
					break;
			case 3:
				cancelBooking(email);
				break;
			case 4:
				editProfile(email);
				break;
			case 5:
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}
}

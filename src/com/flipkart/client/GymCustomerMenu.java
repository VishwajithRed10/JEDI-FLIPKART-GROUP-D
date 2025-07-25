package com.flipkart.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.flipkart.bean.*;
import com.flipkart.business.GymCustomerService;
import com.flipkart.business.GymUserService;
import com.flipkart.utils.IdGenerator;
/*
 *@Author : "Aditi Baveja"
 *@ClassName: "GymCustomerMenu"
 *@Exceptions: "java.text.ParseException"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCustomer, com.flipkart.bean.GymCentre, com.flipkart.bean.Slot, com.flipkart.bean.Booking, com.flipkart.bean.Payment, com.flipkart.business.GymCustomerService, com.flipkart.business.GymUserService, java.util.Scanner"
 */
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
	// --- 1. SEARCH FOR GYMS ---
	System.out.println("\nHow would you like to search for gyms?");
	System.out.println("1. By Address");
	System.out.println("2. By Date");
	System.out.print("Enter your choice: ");
	int choice = sc.nextInt();

	List<GymCentre> gyms = new ArrayList<>();
	if (choice == 1) {
		System.out.print("Enter your address: ");
		gyms = customerBusiness.getGymInCity(sc.next());
	} else if (choice == 2) {
		System.out.print("Enter Date (yyyy-MM-dd): ");
		String dateStr = sc.next();
		gyms = customerBusiness.getGymsByDate(dateStr);
	} else {
		System.out.println("Invalid choice.");
		return;
	}

	if (gyms.isEmpty()) {
		System.out.println("No verified gyms found for your criteria.");
		return;
	}

	// --- 2. DISPLAY GYMS AND GET USER'S GYM CHOICE ---
	System.out.println("\n--------------------------------------------------------------------------");
	System.out.printf("| %-15s | %-20s | %-25s |%n", "GYM ID", "GYM NAME", "ADDRESS");
	System.out.println("--------------------------------------------------------------------------");
	for (GymCentre gym : gyms) {
		System.out.printf("| %-15s | %-20s | %-25s |%n", gym.getGymId(), gym.getGymName(), gym.getAddress());
	}
	System.out.println("--------------------------------------------------------------------------");

	System.out.print("\nEnter the Gym ID you want to see slots for: ");
	String gymId = sc.next();

	// --- 3. DISPLAY SLOTS FOR THE CHOSEN GYM ---
	List<Slot> slots = customerBusiness.getSlotInGym(gymId);
	if (slots.isEmpty()) {
		System.out.println("No available slots for this gym.");
		return;
	}

	System.out.println("\n--- Available Slots for Gym " + gymId + " ---");
	System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |%n", "SLOT ID", "DATE", "START TIME", "END TIME", "SEATS AVAILABLE");
	System.out.println("------------------------------------------------------------------------------------------------");
	for (Slot slot : slots) {
		int availableSeats = slot.getNumOfSeats() - slot.getNumOfSeatsBooked();
		System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |%n", slot.getSlotId(), new SimpleDateFormat("yyyy-MM-dd").format(slot.getDate()), slot.getStartTime(), slot.getEndTime(), availableSeats);
	}
	System.out.println("------------------------------------------------------------------------------------------------");

	// --- 4. GET USER'S SLOT CHOICE AND BOOK ---
	System.out.print("\nEnter the Slot ID you want to book: ");
	String slotId = sc.next();



	Date bookingDate = null;
	for (Slot slot : slots) {
		if (slot.getSlotId().equals(slotId)) {
			bookingDate = slot.getDate();
			break;
		}
	}

	if (bookingDate == null) {
		System.out.println("Invalid Slot ID.");
		return;
	}

	// --- 4. BOOK SLOT AND PROCEED TO PAYMENT ---
	String newBookingId = customerBusiness.bookSlot(gymId, slotId, email, bookingDate);

	if (newBookingId != null) {
		System.out.println("\n*********************************");
		System.out.println("* Slot confirmed. Proceeding... *");
		System.out.println("*********************************\n");

		// --- 5. PAYMENT PORTAL ---
		Payment payment = new Payment();
		payment.setBookingId(newBookingId);
		payment.setTransactionId(IdGenerator.generateId("Trans"));
		payment.setPaymentStatus("Success");

		System.out.println("--- Payment Portal ---");
		System.out.println("1. UPI");
		System.out.println("2. Net Banking");
		System.out.print("Choose your payment method: ");
		int paymentChoice = sc.nextInt();

		if (paymentChoice == 1) {
			payment.setPaymentMethod("UPI");
			System.out.print("Enter your UPI ID: ");
			payment.setPaymentMethodId(sc.next());
		} else if (paymentChoice == 2) {
			payment.setPaymentMethod("Net Banking");
			System.out.print("Enter your Net Banking ID: ");
			payment.setPaymentMethodId(sc.next());
		} else {
			System.out.println("Invalid payment method.");
			// You might want to cancel the booking here if payment fails
			return;
		}

		customerBusiness.makePayment(payment);

		System.out.println("\n--- Payment Successful! ---");
		System.out.println("Booking Confirmed. Your Booking ID is: " + newBookingId);

	} else {
		System.out.println("Booking failed. Please try again.");
	}
}


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

		if (gyms.isEmpty()) {
			System.out.println("No verified gyms found for this address.");
			return;
		}

		System.out.println("\n--------------------------------------------------------------------------");
		System.out.printf("| %-15s | %-20s | %-25s |%n", "GYM ID", "GYM NAME", "ADDRESS");
		System.out.println("--------------------------------------------------------------------------");

		for (GymCentre gym : gyms) {
			System.out.printf("| %-15s | %-20s | %-25s |%n",
					gym.getGymId(),
					gym.getGymName(),
					gym.getAddress()
			);
		}
		System.out.println("--------------------------------------------------------------------------");
	}



	public void cancelBooking(String email) {
		// 1. Fetch and display all current bookings for the user
		List<Booking> bookings = customerBusiness.getBookings(email);

		if (bookings.isEmpty()) {
			System.out.println("You have no bookings to cancel.");
			return;
		}

		System.out.println("\n--- Here are your current bookings ---");
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.printf("| %-20s | %-15s | %-15s | %-15s |%n", "BOOKING ID", "GYM ID", "SLOT ID", "DATE");
		System.out.println("-------------------------------------------------------------------------------------");
		for (Booking booking : bookings) {
			System.out.printf("| %-20s | %-15s | %-15s | %-15s |%n",
					booking.getBookingId(),
					booking.getGymId(),
					booking.getSlotId(),
					new SimpleDateFormat("yyyy-MM-dd").format(booking.getDate())
			);
		}
		System.out.println("-------------------------------------------------------------------------------------");

		// 2. Prompt user to enter the booking ID to cancel
		System.out.print("\nEnter the booking ID that you want to cancel: ");
		String bookingId = sc.next();

		// 3. Call the cancellation logic and provide feedback
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
						System.out.println("\n-------------------------------------------------------------------------------------");
						System.out.printf("| %-20s | %-15s | %-15s | %-15s |%n", "BOOKING ID", "GYM ID", "SLOT ID", "DATE");
						System.out.println("-------------------------------------------------------------------------------------");
						for (Booking booking : bookings) {
							System.out.printf("| %-20s | %-15s | %-15s | %-15s |%n",
									booking.getBookingId(),
									booking.getGymId(),
									booking.getSlotId(),
									new SimpleDateFormat("yyyy-MM-dd").format(booking.getDate())
							);
						}
						System.out.println("-------------------------------------------------------------------------------------");
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

package com.flipkart.client;

import java.util.List;
import java.util.Scanner;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.Slot;
import com.flipkart.business.GymOwnerService;
import com.flipkart.business.GymUserService;
import com.flipkart.constants.ColorConstants;
import com.flipkart.utils.IdGenerator;

public class GymOwnerMenu {

	GymOwner gymOwner = new GymOwner();
	GymOwnerService gymOwnerBusiness = new GymOwnerService();
	GymUserService userBusiness = new GymUserService();

	public void gymOwnerRegistration(Scanner in) {
		System.out.println("\nEnter GymOwner Details: \n");
		System.out.print("Enter Email: ");
		gymOwner.setEmail(in.next());
		System.out.print("Enter Password: ");
		gymOwner.setPassword(in.next());
		gymOwner.setRoleId("GymOwner");
		System.out.print("Enter Name: ");
		gymOwner.setName(in.next());
		System.out.print("Enter Phone Number: ");
		gymOwner.setPhoneNumber(in.next());
		System.out.print("Enter PAN: ");
		gymOwner.setPanNumber(in.next());
		System.out.print("Enter Aadhaar: ");
		gymOwner.setAadharNumber(in.next());
		gymOwner.setVerified(false);

		GymUserService userBusiness = new GymUserService();
		boolean registerSuccess = userBusiness.registerGymOwner(gymOwner);

		if (registerSuccess)
			System.out
					.println("\n" + ColorConstants.GREEN + "Gym Owner registered successfully!" + ColorConstants.RESET);
		else
			System.out.println(
					"\n" + ColorConstants.RED + "Gym Owner registration failed! Try again!" + ColorConstants.RESET);
	}

	public void editProfile(Scanner in, String email) {
		System.out.println("Enter Details: ");
		System.out.print("Enter Email: ");
		gymOwner.setEmail(in.next());
		System.out.print("Enter Password: ");
		gymOwner.setPassword(in.next());
		gymOwner.setRoleId("GymOwner");
		System.out.print("Enter Name: ");
		gymOwner.setName(in.next());
		System.out.print("Enter Phone Number: ");
		gymOwner.setPhoneNumber(in.next());
		System.out.print("Enter PAN: ");
		gymOwner.setPanNumber(in.next());
		System.out.print("Enter Aadhaar: ");
		gymOwner.setAadharNumber(in.next());

		gymOwnerBusiness.editProfile(gymOwner);
	}

	public void viewProfile(Scanner in, String email) {
		gymOwner = gymOwnerBusiness.getProfile(email);
		System.out.println("______________________________________________________________");
		System.out.printf("%15s%15s%15s%15s", "Gym Owner Name", "Phone Number", "PAN Number", "Aadhaar Number");
		System.out.println();
		System.out.printf("%15s%15s%15s%15s", gymOwner.getName(), gymOwner.getPhoneNumber(), gymOwner.getPanNumber(),
				gymOwner.getAadharNumber());
		System.out.println("\n______________________________________________________________");
	}

	public void addGym(Scanner in, String email) {
		System.out.println("Please Enter Gym Details ");

		GymCentre gym = new GymCentre();
		gym.setGymId(IdGenerator.generateId("Gym"));
		System.out.print("Gym Name: ");
		gym.setGymName(in.next());
		gym.setOwnerEmail(email);
		System.out.print("Address: ");
		gym.setAddress(in.next());
		System.out.print("SlotCount: ");
		gym.setSlotCount(in.nextInt());
		System.out.print("SeatsPerSlotCount: ");
		gym.setSeatsPerSlotCount(in.nextInt());
		gym.setVerified(false);

		gymOwnerBusiness.addGym(gym);
	}

	public void editGym(Scanner in, String email) {
		System.out.println("Please Enter Gym Details ");

		GymCentre gym = new GymCentre();
		System.out.print("Gym Id: ");
		gym.setGymId(in.next());
		System.out.print("GymName: ");
		gym.setGymName(in.next());
		gym.setOwnerEmail(email);
		System.out.print("Address: ");
		gym.setAddress(in.next());
		System.out.print("SlotCount: ");
		gym.setSlotCount(in.nextInt());
		System.out.print("SeatsPerSlotCount: ");
		gym.setSeatsPerSlotCount(in.nextInt());
		gym.setVerified(false);

		gymOwnerBusiness.editGym(gym);
	}

	public void getGymDetails(Scanner in, String email) {
		List<GymCentre> gymDetails = gymOwnerBusiness.getGymDetail(email);
		for (GymCentre gym : gymDetails) {
			System.out.println(gym);
		}
	}

	public void addSlot(Scanner in) {
		System.out.println("Enter Slot Details: ");
		Slot slot = new Slot();
		slot.setSlotId(IdGenerator.generateId("Slot"));
		System.out.print("Enter Gym Id:");
		slot.setGymId(in.next());
		System.out.print("Enter Slot Start Time: ");
		slot.setStartTime(in.next());
		System.out.print("Enter Slot End Time: ");
		slot.setEndTime(in.next());
		System.out.print("Enter number of seats in slot: ");
		slot.setNumOfSeats(in.nextInt());
		System.out.print("Enter Trainer: ");
		slot.setTrainer(in.next());
		slot.setNumOfSeatsBooked(0);
		
		gymOwnerBusiness.addSlot(slot);
	}

	public void gymOwnerMenu(Scanner in, String email) {
		boolean recur = true;
		while (recur) {
			System.out.println("\nHere are the actions you can perform!");

			System.out.println("1. View Profile");
			System.out.println("2. Edit Profile");
			System.out.println("3. Add Gym");
			System.out.println("4. Edit Gym");
			System.out.println("5. Add Slot");
			System.out.println("6. View All Gym Details");
			System.out.println("7. LogOut\n");

			System.out.print("Enter Your Choice: " );
			int choice = in.nextInt();

			System.out.println("______________________________________________________________\n");

			switch (choice) {
			case 1:
				viewProfile(in, email);
				break;
			case 2:
				editProfile(in, email);
				break;
			case 3:
				addGym(in, email);
				break;
			case 4:
				editGym(in, email);
				break;
			case 5:
				addSlot(in);
				break;
			case 6:
				getGymDetails(in, email);
				break;
			case 7:
				recur = false;
				break;
			default:
				System.out.println(ColorConstants.RED + "Invalid Choice!" + ColorConstants.RESET);
			}
			if (!recur) {
				gymOwner = new GymOwner();
				boolean logOutSuccess = userBusiness.logout(gymOwner);
				if (logOutSuccess)
					System.out.println(ColorConstants.GREEN + "Logged Out Successfully!" + ColorConstants.RESET);
				else
					System.out.println(ColorConstants.RED + "Logged Out Successfully!" + ColorConstants.RESET);
			}
		}

	}

}

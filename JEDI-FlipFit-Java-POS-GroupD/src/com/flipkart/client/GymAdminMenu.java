package com.flipkart.client;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;
import com.flipkart.business.*;

import java.util.*;

/**
 * 
 */

public class GymAdminMenu {

	GymAdminService adminBusiness = new GymAdminService();

	List<GymOwner> gymOwnerList = adminBusiness.getGymOwners();
	List<GymCentre> gymList = adminBusiness.getGym();
	Scanner sc = new Scanner(System.in);
	
	public void viewAllPendingGymOwnerRequests() {
		viewAllGymOwners(adminBusiness.viewAllPendingGymOwnerRequests());
	}
	public void viewAllPendingGymRequests() {
		viewAllGyms(adminBusiness.viewAllPendingGymRequests());
	}
	
	public void approveSingleGymOwnerRequest() {
		System.out.println("Enter gym owner email: ");
		adminBusiness.approveSingleGymOwnerRequest(sc.next());
	}
	
	public void approveSingleGymRequest() {
		System.out.println("Enter gym Id: ");
		adminBusiness.approveSingleGymRequest(sc.next());
	}

	public void approvePendingGymOwnerRequests() {
		adminBusiness.approveAllPendingGymOwnerRequests();
	}

	public void approvePendingGymRequests() {
		adminBusiness.approveAllPendingGymRequests();
	}

	public void adminMenu(Scanner in) throws Exception {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		while (true) {
			System.out.println("1. View All Gym ");
			System.out.println("2. View All Gym Owners");
			System.out.println("3. View all pending Gym Owner Requests");
			System.out.println("4. View all pending Gym Requests");
			System.out.println("5. Approve all pending Gym Owner Requests");
			System.out.println("6. Approve all pending Gym Requests");
			System.out.println("7. Approve Single Gym Owner Request");
			System.out.println("8. Approve Single Gym Request");
			System.out.println("9. Add New Gym Owner");
			System.out.println("10. Remove Gym Owner"); // Add this line
			System.out.println("11. Exit");

			System.out.print("Enter your choice: ");
			int choice = in.nextInt();
			switch (choice) {
			// Case statements
				 case 1:
					List<GymCentre> gymList = adminBusiness.getGym(); // Fetches fresh data
					viewAllGyms(gymList);
					break;
			case 2:
				viewAllGymOwners(gymOwnerList);
				break;
			case 3:
				viewAllPendingGymOwnerRequests();
				break;
			case 4:
				viewAllPendingGymRequests();
				break;
			case 5:
				approvePendingGymOwnerRequests();
				break;
			case 6:
				approvePendingGymRequests();
				break;
			case 7:
				approveSingleGymOwnerRequest();
				break;
				case 8:
					approveSingleGymRequest();
					break;
				case 9:
					addGymOwner();
					break;
				case 10:
					removeGymOwner(); // Add this case
					break;
				case 11:          // Change exit option number
					return;
			}
		}
}
			
	public void viewAllGyms(List<GymCentre> gyms) {
		for (GymCentre gym : gyms) {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Gym Id-->" + gym.getGymId());
			System.out.println("Gym Name-->" + gym.getGymName());
			System.out.println("Gym Owner Mail-->" + gym.getOwnerEmail());
			System.out.println("Gym Address-->" + gym.getAddress());
			System.out.println("Gym Slot Count-->" + gym.getSlotCount());
			System.out.println("Gym Verification -->" + (gym.isVerified() ? "Yes" : "No"));
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		}
	}

	public void viewAllGymOwners(List<GymOwner> gymOwners) {
		for (GymOwner gymOwner : gymOwners) {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Gym Owner Name-->" + gymOwner.getName());
			System.out.println("Gym Owner phone numver-->" + gymOwner.getPhoneNumber());
			System.out.println("Gym Owner Aadhar-->" + gymOwner.getAadharNumber());
			System.out.println("Gym Owner panNumber-->" + gymOwner.getPanNumber());
			System.out.println("Gym Owner Verification -->" + (gymOwner.isVerified() ? "Yes" : "No"));
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		}

	}

	public void addGymOwner() {
		System.out.println("\nEnter New Gym Owner Details:");
		GymOwner owner = new GymOwner();
		GymUserService userBusiness = new GymUserService();

		System.out.print("Enter Email: ");
		owner.setEmail(sc.next());
		System.out.print("Enter Password: ");
		owner.setPassword(sc.next());
		System.out.print("Enter Name: ");
		owner.setName(sc.next());
		System.out.print("Enter Phone Number: ");
		owner.setPhoneNumber(sc.next());
		System.out.print("Enter PAN: ");
		owner.setPanNumber(sc.next());
		System.out.print("Enter Aadhaar: ");
		owner.setAadharNumber(sc.next());
		owner.setRoleId("GymOwner");
		owner.setVerified(false); // New owners start as unverified

		boolean registered = userBusiness.registerGymOwner(owner);

		if (registered) {
			System.out.println("Gym Owner added successfully! They will need to be approved.");
		} else {
			System.out.println("Failed to add Gym Owner.");
		}
	}

	public void removeGymOwner() {
		System.out.println("\nEnter the email of the Gym Owner to remove:");
		String emailToRemove = sc.next();

		boolean removed = adminBusiness.removeGymOwner(emailToRemove);

		if (removed) {
			System.out.println("Gym Owner with email " + emailToRemove + " has been removed.");
		} else {
			System.out.println("Failed to remove Gym Owner. Please check the email and try again.");
		}
	}
}

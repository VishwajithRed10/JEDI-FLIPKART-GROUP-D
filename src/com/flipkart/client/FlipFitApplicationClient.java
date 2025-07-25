package com.flipkart.client;

import java.util.*;

import com.flipkart.bean.GymUser;
import com.flipkart.business.GymUserService;
import com.flipkart.constants.*;

public class FlipFitApplicationClient {


	/*
	 *@Author : "Ronil Patil"
	 *@ClassName: "FlipFitApplicationClient"
	 *@Exceptions: "java.lang.Exception"
	 *@Version : "1.0"
	 *@See : "com.flipkart.bean.GymUser, com.flipkart.business.GymUserService, com.flipkart.client.GymAdminMenu, com.flipkart.client.GymCustomerMenu, com.flipkart.client.GymOwnerMenu"
	 */
	public static void login() throws Exception {
		Scanner in = new Scanner(System.in);
		String role = "";
		boolean roleSelected = false;

		// --- 1. Ask for the Role First ---
		System.out.println("\n--- Login ---");
		System.out.println("1. Admin");
		System.out.println("2. Gym Owner");
		System.out.println("3. Customer");
		System.out.print("Enter your choice: ");
		int roleChoice = in.nextInt();

		switch (roleChoice) {
			case 1:
				role = "Admin";
				roleSelected = true;
				break;
			case 2:
				role = "GymOwner";
				roleSelected = true;
				break;
			case 3:
				role = "Customer";
				roleSelected = true;
				break;
			default:
				System.out.println("Invalid choice.");
				break;
		}

		if (roleSelected) {
			// --- 2. Get Credentials ---
			System.out.print("Enter Email: ");
			String userEmail = in.next();
			System.out.print("Enter Password: ");
			String password = in.next();

			// --- 3. Authenticate against the Database ---
			GymUser user = new GymUser(userEmail, password, role);
			GymUserService userBusiness = new GymUserService();

			if (userBusiness.authenticateUser(user)) {
				System.out.println("\nWelcome " + userEmail + "! You are logged in.");

				// --- 4. Proceed to the correct menu ---
				if (role.equalsIgnoreCase("Admin")) {
					GymAdminMenu admin = new GymAdminMenu();
					admin.adminMenu(in);
				} else if (role.equalsIgnoreCase("Customer")) {
					GymCustomerMenu customer = new GymCustomerMenu();
					customer.customerMenu(userEmail);
				} else if (role.equalsIgnoreCase("GymOwner")) {
					GymOwnerMenu gymOwner = new GymOwnerMenu();
					gymOwner.gymOwnerMenu(in, userEmail);
				}
			} else {
				System.out.println("Login failed. Please check your credentials and role.");
			}
		}
	}

	public static void applicationMenu() throws Exception {
		boolean recur = true;
		System.out.println(ColorConstants.GREEN + "Welcome to the FlipFit Application!" + ColorConstants.RESET);

		while (recur) {
			System.out.println("\nChoose your action:");
			System.out.println("1. Login");
			System.out.println("2. Customer Registration");
			System.out.println("3. Gym Owner Registration");
			System.out.println("4. Exit");
			System.out.print("\nEnter Your Choice: ");

			Scanner in = new Scanner(System.in);

			int choice = in.nextInt();
			switch (choice) {
			case 1:
				login();
				break;
			case 2:
				GymCustomerMenu customer = new GymCustomerMenu();
				customer.registerCustomer();
				login();
				break;
			case 3:
				GymOwnerMenu owner = new GymOwnerMenu();
				owner.gymOwnerRegistration(in);
				login();
				break;
			case 4:
				System.out.println(ColorConstants.RED + "Exiting..." + ColorConstants.RESET);
				System.out.println(ColorConstants.GREEN + "Exited Successfully" + ColorConstants.RESET);
				recur = false;
				System.exit(0);
				break;
			default:
				System.out.println(ColorConstants.RED + "Wrong choice" + ColorConstants.RESET);
			}
		}

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		applicationMenu();
	}

}

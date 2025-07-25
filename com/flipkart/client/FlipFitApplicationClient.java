package com.flipkart.client;

import java.util.*;

import com.flipkart.bean.GymUser;
import com.flipkart.business.GymUserService;
import com.flipkart.constants.*;

public class FlipFitApplicationClient {

	public static void login() throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("__________________________________________________________________________________\n");
		System.out.println("Enter LogIn Details\n");
		System.out.print("Enter Email: ");
		String userEmail = in.next();
		System.out.print("Enter Password: ");
		String password = in.next();
		System.out.print("Enter Role Name: ");
		String roleId = in.next();
		GymUser user = new GymUser(userEmail, password, roleId);
		GymUserService userBusiness = new GymUserService();
		if (roleId.equalsIgnoreCase("Admin")) {
			GymAdminMenu admin = new GymAdminMenu();
			admin.adminMenu(in);
		} 
		else if (userBusiness.authenticateUser(user)) {
			System.out.println("__________________________________________________________________________________\n");
			System.out.println(
					ColorConstants.GREEN + "Welcome " + userEmail + "! You are logged in." + ColorConstants.RESET);

			if (roleId.equalsIgnoreCase("Customer")) {

				GymCustomerMenu customer = new GymCustomerMenu();
				customer.customerMenu(userEmail);

			} else if (roleId.equalsIgnoreCase("GymOwner")) {

				GymOwnerMenu gymOwner = new GymOwnerMenu();
				gymOwner.gymOwnerMenu(in, userEmail);

			} else {
				System.out.println(ColorConstants.RED + "Wrong Choice!" + ColorConstants.RESET);
			}
		} else {
			System.out.println(ColorConstants.RED + "\nSorry! You are not Registered! Please Register Yourself!" + ColorConstants.RESET);
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

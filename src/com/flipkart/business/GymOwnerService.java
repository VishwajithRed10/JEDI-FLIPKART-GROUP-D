/**
 * 
 */
package com.flipkart.business;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.Slot;
import com.flipkart.DAO.*;
import com.flipkart.constants.ColorConstants;

import java.util.*;

/*
 *@Author : "Aditi Baveja"
 *@ClassName: "GymOwnerService"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.DAO.GymOwnerDAOImpl, com.flipkart.bean.GymCentre, com.flipkart.bean.GymOwner, com.flipkart.bean.Slot"
 */
public class GymOwnerService implements GymOwnerServiceInterface {
	GymOwnerDAOImpl gymOwnerDAO = new GymOwnerDAOImpl();


	public GymOwner getProfile(String email) {
		System.out.println(ColorConstants.GREEN +"Fetched Gym owner details successfully! " + email+ColorConstants.RESET);
		return gymOwnerDAO.getGymOwnerDetails(email);
	}

	public void editProfile(GymOwner gymOwnerNew) {
		gymOwnerDAO.editGymOwnerDetails(gymOwnerNew);
		System.out.println(ColorConstants.GREEN + "\nEdited your profile Successfully!" + ColorConstants.RESET);
	}

	public boolean addGym(GymCentre gym) {
		// First, check if the owner is approved
		if (!isApproved(gym.getOwnerEmail())) {
			System.out.println("\nYour registration is not yet approved by the Admin. You cannot add a gym.");
			return false;
		}

		// If approved, proceed to add the gym
		gymOwnerDAO.addGym(gym);
		System.out.println("\nAdded Gym Successfully! Your gym is now pending for approval.");
		return true;
	}

	public void editGym(GymCentre gym) {
		// First, check if the owner is approved
		if (!isApproved(gym.getOwnerEmail())) {
			System.out.println("\nYour registration is not yet approved by the Admin. You cannot edit a gym.");
			return;
		}

		// If approved, proceed to edit the gym
		gymOwnerDAO.editGym(gym);
		System.out.println("\nEdited Gym Details Successfully! " + gym.getGymId());
	}

	public List<GymCentre> getGymDetail(String gymOwnerEmail) {
		System.out.println(ColorConstants.GREEN +"\nFetched gym details successfully! " + gymOwnerEmail+ ColorConstants.RESET);
		return gymOwnerDAO.getGymsOfGymOwner(gymOwnerEmail);
	}

	public void addSlot(Slot slot) { // The signature will be updated in the next step
//		// First, check if the owner is approved
		if (!isGymApproved(slot.getGymId())) { // This is a placeholder, as ownerEmail isn't available
			System.out.println("\nYour registration is not yet approved by the Admin. You cannot add a slot.");
			return;
		}

		// If approved, proceed to add the slot
		gymOwnerDAO.addSlot(slot);
		System.out.println("\nAdded slot successfully!");
	}

	public boolean isApproved(String email) {
		return gymOwnerDAO.checkOwnerApproval(email);
	}

	public boolean isGymApproved(String gymId) {
		return gymOwnerDAO.checkGymApproval(gymId);
	}

	public List<Slot> getSlotsForGym(String gymId) {
		return gymOwnerDAO.fetchSlotsForGym(gymId);
	}
}

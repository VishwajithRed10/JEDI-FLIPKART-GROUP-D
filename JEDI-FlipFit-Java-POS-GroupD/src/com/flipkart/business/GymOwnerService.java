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

/**
 * 
 */
public class GymOwnerService implements GymOwnerServiceInterface {
	GymOwnerDAOImpl gymOwnerDAO = new GymOwnerDAOImpl();

	/**
	 * Obtains gym owner's profile details 
	 * @param email the email of the gym owner whose profile details are requested
	 * @return GymOwner the gym owner object
	 */
	public GymOwner getProfile(String email) {
		System.out.println(ColorConstants.GREEN +"Fetched Gym owner details successfully! " + email+ColorConstants.RESET);
		return gymOwnerDAO.getGymOwnerDetails(email);
	}
	/**
	 * Gives functionality of updating gym onwer's personal data. 
	 * @param gymOwnerNew the gymOwner object in which the profile data needs to be updated
	 * @param email the gymOwner email for which the profile data needs to be update
	 */
	public void editProfile(GymOwner gymOwnerNew) {
		gymOwnerDAO.editGymOwnerDetails(gymOwnerNew);
		System.out.println(ColorConstants.GREEN + "\nEdited your profile Successfully!" + ColorConstants.RESET);
	}
	/**
	 * This method allows a gym owner to add details of a particular gym.
	 * @param gym the gym object representing the gym details
	 */
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
	/**
	 * This method allows a gym owner to edit details of a particular gym.
	 * @param gym the gym object representing the gym details
	 */
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
	/**
	 * Obtains all the gyms that owned by the given gym owner.
	 * @param gymOwnerEmail the gym owner's email for which the list of gyms is requested
	 * @return list of gyms owned by the given gym owner
	 */
	public List<GymCentre> getGymDetail(String gymOwnerEmail) {
		System.out.println(ColorConstants.GREEN +"\nFetched gym details successfully! " + gymOwnerEmail+ ColorConstants.RESET);
		return gymOwnerDAO.getGymsOfGymOwner(gymOwnerEmail);
	}
	/**
	 * This method allows a gym owner to add details of a slot.
	 * @param slot the slot object representing the slot details
	 */
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
	/**
	 * Checks if the gym owner is verified or not.
	 * @param email the gym owner's email 
	 * @return true if the gym owner is verified else returns false;
	 */
	public boolean isApproved(String email) {
		return gymOwnerDAO.checkOwnerApproval(email);
	}
	/**
	 * Checks if the gym is verified or not.
	 * @param gymId the gym id for which the verification status is requested
	 * @return true if the gym is verified else returns false;
	 */
	public boolean isGymApproved(String gymId) {
		return gymOwnerDAO.checkGymApproval(gymId);
	}
}

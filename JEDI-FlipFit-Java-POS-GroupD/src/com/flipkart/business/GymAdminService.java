/**
 * 
 */
package com.flipkart.business;
import java.util.*;

import com.flipkart.DAO.GymAdminDAOImpl;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;

/**
 * This class gives the Gym Management System's admin operations capabilities by implementing the AdminGMSInterface.
 */
public class GymAdminService implements GymAdminServiceInterface {
	GymAdminDAOImpl adminDAO = new GymAdminDAOImpl();
	/**
	 * Obtains a list of every gym owner within the system.
	 * @return List of GymOwner objects
	 */
	public List<GymOwner> getGymOwners() {
		System.out.println("Fetched gym owner details successfully!");
		return adminDAO.getAllGymOwners();
	}
	/**
	 * Obtains a list of every gym within the system.
	 * @return List of Gym objects
	 */
	public List<GymCentre> getGym() {
		System.out.println("Fetched gym details successfully!");
		return adminDAO.getAllGyms();
	}

	/**
	 * Returns all GymOwners object whose requests are pending for approval.
	 * @return List of GymOwner objects
	 */
	public List<GymOwner> viewAllPendingGymOwnerRequests() {
		System.out.println("Fetched pending gym owner details successfully!");
		return adminDAO.getPendingGymOwnerRequests();
	}

	/**
	 * Accepts one request from a gym owner. 
	 * @param gymOwnerEmail The request's email that has to be approved
	 */
	public boolean approveSingleGymOwnerRequest(String gymOwnerEmail) {
		adminDAO.approveSingleOwnerRequest(gymOwnerEmail);
		System.out.println("Approved gym owner request! " + gymOwnerEmail);
		return true;
	}

	/**
	 * Approves all GymOwners whose requests are pending for approval. 
	 */
	public boolean approveAllPendingGymOwnerRequests() {
		adminDAO.approveAllOwnerRequest();
		System.out.println("Approved all pending gym owner requests!");
		return true;
	}
	/**
	 * Returns all Gym object whose requests are pending for approval. 
	 * @return List of Gym objects
	 */
	public List<GymCentre> viewAllPendingGymRequests() {
		System.out.println("Fetched pending gym requests successfully!");
		return adminDAO.getPendingGymRequests();
	}
	/**
	 * Approves a single Gym object request. 
	 * @param gymId the id of a gym that needs to be approved
	 * @return true if the gymId is valid else returns false
	 */
	public boolean approveSingleGymRequest(String gymId) {
		adminDAO.approveSingleGymRequest(gymId);
		System.out.println("Successfully approved gym request! " + gymId);
		return true;
	}
	/**
	 * Approves all Gym whose requests are pending for approval. 
	 */
	public boolean approveAllPendingGymRequests() {
		adminDAO.approveAllGymRequest();
		System.out.println("Successfully approved all pending gym requests!");
		return true;
	}

	public boolean removeGymOwner(String email) {
		return adminDAO.deleteGymOwner(email);
	}
}

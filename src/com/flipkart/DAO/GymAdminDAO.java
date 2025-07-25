package com.flipkart.DAO;

import java.util.List;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;
/*
 *@Author : "Dharini"
 *@ClassName: "GymAdminDAO"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCentre, com.flipkart.bean.GymOwner, java.util.List"
 */
public interface GymAdminDAO {
	public List<GymOwner> getAllGymOwners();

	public List<GymCentre> getAllGyms();

	public List<GymOwner> getPendingGymOwnerRequests();

	public List<GymCentre> getPendingGymRequests();

	public void approveSingleOwnerRequest(String gymOwnerEmail);

	public void approveAllOwnerRequest();

	public void approveSingleGymRequest(String gymId);

	public void approveAllGymRequest();

	public boolean deleteGymOwner(String email);
}

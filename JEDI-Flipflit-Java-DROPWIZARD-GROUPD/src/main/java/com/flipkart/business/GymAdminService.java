/**
 *
 */
package com.flipkart.business;
import java.util.*;

import com.flipkart.DAO.GymAdminDAOImpl;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;

/*
 *@Author : "Neelu Lalchandani"
 *@ClassName: "GymAdminService"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.DAO.GymAdminDAOImpl, com.flipkart.bean.GymCentre, com.flipkart.bean.GymOwner"
 */
public class GymAdminService implements GymAdminServiceInterface {
    GymAdminDAOImpl adminDAO = new GymAdminDAOImpl();

    public List<GymOwner> getGymOwners() {
        System.out.println("Fetched gym owner details successfully!");
        return adminDAO.getAllGymOwners();
    }

    public List<GymCentre> getGym() {
        System.out.println("Fetched gym details successfully!");
        return adminDAO.getAllGyms();
    }


    public List<GymOwner> viewAllPendingGymOwnerRequests() {
        System.out.println("Fetched pending gym owner details successfully!");
        return adminDAO.getPendingGymOwnerRequests();
    }


    public boolean approveSingleGymOwnerRequest(String gymOwnerEmail) {
        adminDAO.approveSingleOwnerRequest(gymOwnerEmail);
        System.out.println("Approved gym owner request! " + gymOwnerEmail);
        return true;
    }


    public boolean approveAllPendingGymOwnerRequests() {
        adminDAO.approveAllOwnerRequest();
        System.out.println("Approved all pending gym owner requests!");
        return true;
    }

    public List<GymCentre> viewAllPendingGymRequests() {
        System.out.println("Fetched pending gym requests successfully!");
        return adminDAO.getPendingGymRequests();
    }

    public boolean approveSingleGymRequest(String gymId) {
        adminDAO.approveSingleGymRequest(gymId);
        System.out.println("Successfully approved gym request! " + gymId);
        return true;
    }

    public boolean approveAllPendingGymRequests() {
        adminDAO.approveAllGymRequest();
        System.out.println("Successfully approved all pending gym requests!");
        return true;
    }

    public boolean removeGymOwner(String email) {
        return adminDAO.deleteGymOwner(email);
    }

    @Override
    public List<GymCentre> getGymsByDate(String dateStr) {
        return List.of();
    }


}
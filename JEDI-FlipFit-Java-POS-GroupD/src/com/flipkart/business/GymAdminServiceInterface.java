package com.flipkart.business;

import com.flipkart.bean.*;
import java.util.*;
/*
 *@Author : "Dharini"
 *@ClassName: "GymAdminServiceInterface"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCentre, com.flipkart.bean.GymOwner"
 */
public interface GymAdminServiceInterface {

    public List<GymOwner> getGymOwners();
   

    public List<GymCentre> getGym() ;
    

    public List<GymOwner> viewAllPendingGymOwnerRequests();
    

    public boolean approveSingleGymOwnerRequest(String gymOwnerEmail);
   

    public boolean approveAllPendingGymOwnerRequests();
    

    public List<GymCentre> viewAllPendingGymRequests();
    

    public boolean approveSingleGymRequest(String gymId);
   

    public boolean approveAllPendingGymRequests();

    public boolean removeGymOwner(String email);

    public List<GymCentre> getGymsByDate(String dateStr);
    
}

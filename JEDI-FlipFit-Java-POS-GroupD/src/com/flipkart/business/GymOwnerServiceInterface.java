package com.flipkart.business;

import com.flipkart.bean.*;
import java.util.*;
/*
 *@Author : "Neelu Lalchandani"
 *@ClassName: "GymOwnerServiceInterface"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCentre, com.flipkart.bean.GymOwner, com.flipkart.bean.Slot"
 */
public interface GymOwnerServiceInterface {
    public GymOwner getProfile(String email);

    public void editProfile(GymOwner gymOwnerNews);


    public boolean addGym(GymCentre gym);


    public void editGym(GymCentre gym);


    public List<GymCentre> getGymDetail(String gymOwnerEmail);


    public boolean isApproved(String email);


    public boolean isGymApproved(String gymId);

    public List<Slot> getSlotsForGym(String gymId);

}
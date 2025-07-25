package com.flipkart.business;

import com.flipkart.bean.*;

public interface GymUserServiceInterface {
	 /*
    Registers a new customer
    @return boolean value indicating success of registration
    */
    public boolean registerCustomer(GymCustomer customer);
   
    /*
    Registers a new Gym Owner
    @return boolean value indicating success of registration
    */
    public boolean registerGymOwner(GymOwner gymOwner);
  
    /*
    Authenticates a user
    @return boolean value indicating if user is authenticated
    */
    public boolean authenticateUser(GymUser user);
    
    /*
    Logs out a user
    @return boolean value indicating success of logout
    */
    public boolean logout(GymUser user);
    
}
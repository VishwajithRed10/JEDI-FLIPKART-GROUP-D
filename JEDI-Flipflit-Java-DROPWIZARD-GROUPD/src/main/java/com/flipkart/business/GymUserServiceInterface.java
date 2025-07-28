package com.flipkart.business;

import com.flipkart.bean.*;
/*
 *@Author : "Dharini"
 *@ClassName: "GymUserServiceInterface"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCustomer, com.flipkart.bean.GymOwner, com.flipkart.bean.GymUser"
 */
public interface GymUserServiceInterface {

    public boolean registerCustomer(GymCustomer customer);


    public boolean registerGymOwner(GymOwner gymOwner);


    public boolean authenticateUser(GymUser user);


    public boolean logout(GymUser user);

}
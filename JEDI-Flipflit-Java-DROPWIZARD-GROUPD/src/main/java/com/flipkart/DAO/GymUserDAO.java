package com.flipkart.DAO;

import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.GymUser;
/*
 *@Author : "Ronil Patil"
 *@ClassName: "GymUserDAO"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCustomer, com.flipkart.bean.GymOwner, com.flipkart.bean.GymUser"
 */
public interface GymUserDAO {
    public boolean authenticateUser(GymUser user);

    public boolean registerCustomer(GymCustomer customer);

    public boolean registerGymOwner(GymOwner gymOwner);

}
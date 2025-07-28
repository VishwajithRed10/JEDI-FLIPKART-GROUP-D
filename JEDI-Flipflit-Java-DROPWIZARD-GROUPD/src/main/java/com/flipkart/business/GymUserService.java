/**
 *
 */
package com.flipkart.business;

import com.flipkart.DAO.GymOwnerDAOImpl;
import com.flipkart.DAO.GymUserDAOImpl;
import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.GymUser;

/*
 *@Author : "Sajid"
 *@ClassName: "GymUserService"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.DAO.GymUserDAOImpl, com.flipkart.bean.GymCustomer, com.flipkart.bean.GymOwner, com.flipkart.bean.GymUser"
 */
public class GymUserService implements GymUserServiceInterface {

    GymOwnerDAOImpl gymOwnerDao = new GymOwnerDAOImpl();
    GymUserDAOImpl userDao = new GymUserDAOImpl();

    public boolean registerCustomer(GymCustomer customer) {
        boolean registerSuccess = false;
        registerSuccess = userDao.registerCustomer(customer);
        return registerSuccess;
    }

    public boolean registerGymOwner(GymOwner gymOwner) {
        boolean registerSuccess = false;
        registerSuccess = userDao.registerGymOwner(gymOwner);
        return registerSuccess;
    }

    public boolean authenticateUser(GymUser user) {
        boolean authenticateSuccess = false;
        authenticateSuccess = userDao.authenticateUser(user);
        return authenticateSuccess;
    }

    public boolean logout(GymUser user) {
        return true;
    }
}
package com.flipkart.DAO;

import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.GymUser;

public interface GymUserDAO {
	public boolean authenticateUser(GymUser user);

	public boolean registerCustomer(GymCustomer customer);

	public boolean registerGymOwner(GymOwner gymOwner);

}

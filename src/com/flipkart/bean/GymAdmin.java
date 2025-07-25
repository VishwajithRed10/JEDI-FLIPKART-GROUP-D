/**
 * 
 */
package com.flipkart.bean;

/*
 *@Author : "Neelu Lalchandani"
 *@ClassName: "GymAdmin"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymUser"
 */
public class GymAdmin extends GymUser {
	private String name;
	private String phoneNumber;
	
	public GymAdmin(String email, String password, String roleName, String name, String phoneNumber) {
		super(email, password, roleName);
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public GymAdmin() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

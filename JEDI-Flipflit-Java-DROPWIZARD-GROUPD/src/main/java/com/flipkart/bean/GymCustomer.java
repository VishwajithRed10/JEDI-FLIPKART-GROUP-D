/**
 * 
 */
package com.flipkart.bean;

/*
 *@Author : "Ronil Patil"
 *@ClassName: "GymCustomer"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymUser"
 */
public class GymCustomer extends GymUser {
	private String name;
	private String phoneNumber;
	private int age;
	private String address;
	
	public GymCustomer(String email, String password, String roleName, String name, String phoneNumber, int age, String address) {
		super(email, password, roleName);
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.address = address;
	}
	
	public GymCustomer() {
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

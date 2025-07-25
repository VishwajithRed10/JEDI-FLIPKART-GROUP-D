/**
 * 
 */
package com.flipkart.bean;
/*
 *@Author : "Aditi Baveja"
 *@ClassName: "GymUser"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "N/A"
 */

public class GymUser {
	private String email;
	private String password;
	private String roleId;
	
	public GymUser(String email, String password, String roleId) {
		this.email = email;
		this.password = password;
		this.roleId = roleId;
	}
	
	public GymUser() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}

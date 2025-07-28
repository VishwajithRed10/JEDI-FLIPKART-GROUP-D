/**
 *
 */
package com.flipkart.bean;

import java.util.Date;

/*
 *@Author : "Dharini"
 *@ClassName: "Registration"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "java.util.Date"
 */
public class Registration {

    public String RegistrationId;
    public Date RegistrationDate;
    public String email;

    public String getRegistrationId() {
        return RegistrationId;
    }
    public void setRegistrationId(String registrationId) {
        RegistrationId = registrationId;
    }
    public Date getRegistrationDate() {
        return RegistrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        RegistrationDate = registrationDate;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}

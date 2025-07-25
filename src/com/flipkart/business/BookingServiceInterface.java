package com.flipkart.business;
/*
 *@Author : "Aditi Baveja"
 *@ClassName: "BookingServiceInterface"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "N/A"
 */
public interface BookingServiceInterface {

    public boolean isConfirmed(String bookingId);
   

    public int getWaitingList();
}
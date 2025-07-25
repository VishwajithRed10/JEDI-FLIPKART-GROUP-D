/**
 * 
 */
package com.flipkart.business;

import com.flipkart.bean.*;
import java.util.*;
import java.util.Date;
/*
 *@Author : "Vishwajith"
 *@ClassName: "BookingService"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.Booking"
 */
public class BookingService implements BookingServiceInterface {

	List<Booking> bookings=new ArrayList<>();
	Date d1=new Date(); //current date


	public boolean isConfirmed(String bookingId) {

		for(Booking b:bookings)
		{
			if(b.getBookingId().equals(bookingId))
			{
				if(b.getType()=="confirmed")
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public int getWaitingList() {
		return -1;
	}

}

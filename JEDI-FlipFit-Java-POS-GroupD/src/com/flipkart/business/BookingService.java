/**
 * 
 */
package com.flipkart.business;

import com.flipkart.bean.*;
import java.util.*;
import java.util.Date;
/**
 * 
 */public class BookingService implements BookingServiceInterface {

	List<Booking> bookings=new ArrayList<>();
	Date d1=new Date(); //current date

	Booking b1=new Booking("123","121","171","confirmed",d1,"c1@gmail.com","John");
	Booking b2=new Booking("173","191","131","waitlisted",d1,"c2@gmail.com","Jack");
	Booking b3=new Booking("113","129","173","confirmed",d1,"c3@gmail.com","Johnathon");
	Booking b4=new Booking("193","127","971","waitlisted",d1,"c4@gmail.com","J");

	public BookingService()
	{
		bookings.add(b1);
		bookings.add(b2);
		bookings.add(b3);
		bookings.add(b4);
	}
	/**
	 * Checks if a booking is confirmed or not for the given bookingId
	 * @param bookingId the id of a booking that needs to be checked
	 * @return true if the bookingId is confirmed else returns false
	 */
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
	/**
	 * Gives a size of wait listed customers.
	 */
	public int getWaitingList() {
		return -1;
	}

}

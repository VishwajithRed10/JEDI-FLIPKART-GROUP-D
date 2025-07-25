/**
 * 
 */
package com.flipkart.bean;
/*
 *@Author : "Neelu Lalchandani"
 *@ClassName: "Payment"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "N/A"
 */
public class Payment {
	private String transactionId;
	private String bookingId;
	private String paymentStatus;
	private String paymentMethod;
	private String paymentMethodId;

	// Getters and Setters for all fields
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
}

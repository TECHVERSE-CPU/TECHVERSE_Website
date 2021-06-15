package com.techverse.entity;

public class Sms {
	
	private String phoneNumber; // destination

	private String message;

	public Sms(String phoneNumber, String message) {
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "SmsRequest{" + "phoneNumber= ..." + '\'' + ", message='" + message + '\'' + '}';
	}

}

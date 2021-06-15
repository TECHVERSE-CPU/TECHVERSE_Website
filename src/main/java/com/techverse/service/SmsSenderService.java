package com.techverse.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.techverse.entity.Phone;
import com.techverse.entity.Sms;


public interface SmsSenderService {
	
	int sendSms(Sms smsRequest);
	
	Phone save(String phoneNo, int otp, Date date); // save otp and mobile no
	
	String getOtpDetailsForSms(String phoneNo, int otp); // get otp and mobile no verify
	

}

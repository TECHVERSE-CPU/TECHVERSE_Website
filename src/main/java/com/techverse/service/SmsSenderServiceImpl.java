package com.techverse.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.config.TwilioConfiguration;
import com.techverse.entity.Phone;
import com.techverse.entity.Sms;
import com.techverse.repository.UserRepository;
import com.techverse.util.OTPNumberGenUtil;
import com.techverse.util.StringConstants;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderServiceImpl.class);

    private final TwilioConfiguration twilioConfiguration;

    
    @Autowired(required=true)
    UserRepository userRepository;
    //repo
    
    @Autowired
    public SmsSenderServiceImpl(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public int sendSms(Sms smsRequest) {
    	int otp = 0;
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            otp = generateOtpNumber();
            message = message+"-"+otp;
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}", smsRequest);
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }
        return otp;
    }
    
    public static int generateOtpNumber(){
    	Random random = new Random();
    	int otp = 10000 + random.nextInt(90000);
    	return otp;
   }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }

	public Phone save(String phoneNo, int otp, Date date) {
		// call here dynamo repo to save both details
		Phone otpRequest = new Phone();
		otpRequest.setOtp(otp);
		otpRequest.setPhoneNo(phoneNo);
		otpRequest.setCreatedDate(date);
		userRepository.save(otpRequest);
		return otpRequest;
	}

	public String getOtpDetailsForSms(String phoneNo, int otp) {
		// get otp and mobile no from db for verify
		Date previous =userRepository.getOTPDetialsForSms(phoneNo, otp);
		
		if(previous != null){
			LocalDateTime oldDate = LocalDateTime.ofInstant(previous.toInstant(),ZoneId.systemDefault());
			System.out.println("OLD DATE : " + oldDate);
			LocalDateTime newDate = LocalDateTime.now();
			System.out.println("CURRENT DATE : " + newDate);
	  
	        //count minutes between dates
	        Duration duration = Duration.between(oldDate, newDate);
	        System.out.println(duration.toMinutes() + " minutes");
	        
	        if(duration.toMinutes()<5) 
			{
				return StringConstants.SUCCESS;
			}else{		
				return StringConstants.FAIL;
			}
		}else{
			return StringConstants.INVALID;
		}
	}


}

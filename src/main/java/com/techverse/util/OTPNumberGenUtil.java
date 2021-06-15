package com.techverse.util;

import java.util.Random;

public class OTPNumberGenUtil {
	
	public static int generateOtpNumber(){
    	Random random = new Random();
    	int otp = 10000 + random.nextInt(90000);
    	return otp;
   }

}

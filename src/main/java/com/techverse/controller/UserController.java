package com.techverse.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.entity.Meeting;
import com.techverse.entity.Phone;
import com.techverse.entity.Sms;
import com.techverse.entity.User;
import com.techverse.service.SmsSenderService;
import com.techverse.service.UserService;
import com.techverse.util.StringConstants;


@RestController
@RequestMapping("/techversewebsite")
public class UserController  {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);
	
    
    @Autowired(required=true)
    private SmsSenderService smsSenderService;
	
	@Autowired
	private UserService userService;
  
	
	@GetMapping("/index")
	public ResponseEntity<String> index(){
		LOGGER.info("UserController - index() :: Start");
		String message = "Welcome to Broker API...!";
		LOGGER.info("UserController - index() :: End");
		return ResponseEntity.ok().body(message);		
	}
	

	
    @PostMapping("/createuser")
    public User saveEmployee(@RequestBody User user) {
    	 User userEmployee = userService.saveEmployee(user);
         return userEmployee;
    }

    
	
    //sent mail for meeting and save data
    @RequestMapping(value="/customer/save", method ={RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<String> saveMeetingandsendEmail(@RequestBody Meeting meeting) {
    	System.out.println("enter controller");
    	userService.saveMeetingandsendEmail(meeting);
        // return "User Profile entry  deleted with number : ", HttpStatus.OK;
        return new ResponseEntity<String>("Meeting details saved..",HttpStatus.OK);
	}
        // return new ResponseEntity<Meeting>("Your number is Verified",HttpStatus.OK);
    
    
        	
    @PostMapping("/sendotp")
	public ResponseEntity<String> sendotp(@RequestParam("phone") String phone)
	{
    	// sending otp and saving in db
    	int otp=0;
    	Phone otpReq = null;
		if(phone != null){
			Sms smsRequest = new Sms(phone, StringConstants.OTP_MESSAGE);
			otp = smsSenderService.sendSms(smsRequest);
		}
		if(otp != 0){
			otpReq = smsSenderService.save(phone, otp, new Date());
		}
		
		if(otpReq != null && otp != 0) {
			return new ResponseEntity<String>("Otp sent..",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Otp sent..",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<String> sendotp(@RequestParam("phone") String phone, @RequestParam("otp") String otp)
	{
	    
		int otpno = Integer.valueOf(otp);
	    // we need to check db means u have to save date and time in db also then only u compare date and time
	    if(phone!=null && otp != null)
	    {
	    	String message = smsSenderService.getOtpDetailsForSms(phone, otpno);
		    if(message == StringConstants.SUCCESS) {
		    	return new ResponseEntity<String>("Your number is Verified",HttpStatus.OK);
		    }else if(message == StringConstants.FAIL) {
		    	return new ResponseEntity<String>("Something wrong/ Otp incorrect",HttpStatus.BAD_REQUEST);
		    }else {
		    	return new ResponseEntity<String>("Something wrong/ Otp incorrect",HttpStatus.BAD_REQUEST);
		    }
		    	
	    }
	    return new ResponseEntity<String>("Something wrong/ Otp incorrect",HttpStatus.BAD_REQUEST);
	}
	
	
	
    	
    	
}



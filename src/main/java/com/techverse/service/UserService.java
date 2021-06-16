package com.techverse.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.techverse.entity.Meeting;
import com.techverse.entity.User;

public interface UserService {

	public User saveEmployee(User user);

	public User getUserById(String userId);

	public String deleteEmployee(String userId);

	public String updateEmployee(String userId, User user);

	public ResponseEntity<String> saveFormandMeetingData(Meeting meeting);

	public List<User> getUserStatus(String status);

	
	public ResponseEntity<String> saveMeetingandsendEmail(Meeting meeting);
}

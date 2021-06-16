package com.techverse.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.techverse.entity.Meeting;
import com.techverse.entity.User;
import com.techverse.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSenderObj;

	private static final String MAIL_RECEPTENT = "pediredlanagarevathi@gmail.com";

	public User saveEmployee(User user) {
		user.setStatus("1");
		User userEmployee = userRepository.save(user);
		sendMail(user, user.getEmailId());
		return userEmployee;
	}

	private void sendMail(User user, String userEmailId) {
		// TODO Auto-generated method stub

		final String emailToRecipient = MAIL_RECEPTENT;
		final String emailFromRecipient = userEmailId;
		final String emailSubject = "Enquiry Done ";

		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>You have succesfully Registered with our Services"
				+ "<br><br>"
				// + "<table border='1' width='300px'
				// style='text-align:center;font-size:20px;'><tr> <td colspan='2'>"
				+ "<tr><td>Name</td><td>" + user.getName() + "</td></tr><tr><td>Email</td><td>" + user.getEmailId()
				+ "</td></tr><tr><td>Message</td><td>" + user.getMessage() + "</td></tr> </body></html>";

		mailSenderObj.send(new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setFrom("test@gmail.com");

				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);

			}
		});

	}

	
	public ResponseEntity<String> saveMeetingandsendEmail(Meeting meeting) {
		userRepository.save(meeting);
		sendmailformeeting(meeting, meeting.getEmail());
		return new ResponseEntity<String>("Meeting details saved..", HttpStatus.OK);
	}

	private void sendmailformeeting(Meeting meeting, String email) {
		// TODO Auto-generated method stub
		final String emailToRecipient = MAIL_RECEPTENT;
		final String emailFromRecipient = email;
		final String emailSubject = "Meeting Schedule Notification ";

		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>Meeting has been schedule by client as per below details"
				+ "<br><br>"
				// + "<table border='1' width='300px'
				// style='text-align:center;font-size:20px;'><tr> <td colspan='2'>"
				+ "<tr><td>Name</td><td>" + meeting.getFullname() + "</td></tr><tr><td>Email</td><td>"
				+ meeting.getEmail() + "</td></tr><tr><td>Message</td><td>" + meeting.getDateandtime()
				+ "</td></tr><tr><td>Message</td><td>" + meeting.getTitle() + "</td></tr> </body></html>";

		mailSenderObj.send(new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setFrom("test@gmail.com");

				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);

			}
		});

	}

	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String deleteEmployee(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateEmployee(String userId, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getUserStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<String> saveFormandMeetingData(Meeting meeting) {
		// TODO Auto-generated method stub
		userRepository.save(meeting);
        return new ResponseEntity<String>("formandMeeting details saved..", HttpStatus.OK);
	}

	

}

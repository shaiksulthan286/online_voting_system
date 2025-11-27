package com.klef.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.dto.EmailDTO;

import jakarta.mail.internet.MimeMessage;

@RestController
@CrossOrigin("*")
public class EmailController 
{
	@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping("/sendemail")
	public ResponseEntity<String> sendEmail(@RequestBody EmailDTO mailDTO)
	{
	  try 
	  {
		  System.out.println(mailDTO.toString());
		  
		  MimeMessage mimeMessage = mailSender.createMimeMessage();
		   MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		   
		   int otp = (int)(Math.random() * 99999); // random number generation
		   helper.setTo(mailDTO.getEmail());
		   helper.setSubject(mailDTO.getSubject());
		   helper.setFrom("thotabalanagasri@gmail.com"); // eg: demo@gmail.com (which you given in application.properties
		   
		   String htmlContent =
		"<h3>Contact Form Details</h3>" +
		"<p><strong>Name:</strong> " + mailDTO.getName() + "</p>" +
		"<p><strong>Email:</strong> " + mailDTO.getEmail() + "</p>" +
		"<p><strong>Subject:</strong> " + mailDTO.getSubject() + "</p>" +
		"<p><strong>Message:</strong> " + mailDTO.getMessage() + "</p>" +
		"<p><strong>Location:</strong> " + mailDTO.getLocation() + "</p>" +
		"<p><strong>OTP:</strong> " + otp + "</p>";
		
		   helper.setText(htmlContent, true);
		   mailSender.send(mimeMessage);
		   
		   return ResponseEntity.ok("Email Sent Successfully");
      } 
	  catch (Exception e) 
	  {
		  return ResponseEntity.status(500).body("Error in Sending Email: " + e.getMessage());
	  }
	}
}

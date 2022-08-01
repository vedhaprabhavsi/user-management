package com.cognizant.usermanagement.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
	
	private String username;
	private String password;
	
	private String firstName;
	private String lastName;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dob;
}

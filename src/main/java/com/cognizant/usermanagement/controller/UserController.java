package com.cognizant.usermanagement.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.usermanagement.model.AuthenticationRequest;
import com.cognizant.usermanagement.model.AuthenticationResponse;
import com.cognizant.usermanagement.model.User;
import com.cognizant.usermanagement.repository.UserRepository;
import com.cognizant.usermanagement.service.UserService;
import com.cognizant.usermanagement.utils.JwtUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserService service;
	
	@Autowired
	private JwtUtils jwtUtils;
	
//	@Autowired
//	private PasswordEncoder encoder;
	
	@GetMapping("/test")
	@ApiOperation(value = "Test Request", notes = "Header is not required", response = List.class)
	private String testToken(){
		return "Spring Security with JWT is working. Congrats "+SecurityContextHolder.getContext().getAuthentication().getName()+"!!";
	}
	
	@PostMapping("/signup")
	@ApiOperation(value = "User Signup", notes = "Header is not required", response = List.class)
	private ResponseEntity<?> signup(@RequestBody AuthenticationRequest req) throws Exception {
		if(service.loadUserByUsername(req.getUsername())==null){
			String username = req.getUsername();
			String password = req.getPassword();
			String fName = req.getfName();
			String lName = req.getlName();
			Date dob = req.getDob();
			User u = new User();
			u.setUsername(username);
			//u.setPassword(encodePassword(password));
			u.setPassword(password);
			u.setfName(fName);
			u.setlName(lName);
			u.setDob(dob);
			
			System.out.println("User Object value is: "+u.getfName()+" "+u.getlName()+" "+u.getDob());
			try{
				repo.save(u);
			}
			catch(Exception e){
				//logger.error(e.getMessage());
				throw new Exception("Error during client signup: "+username);
				//return ResponseEntity.ok(new AuthenticationResponse("Error during client signup: "+username+"\n"+e.getMessage()));
			}
			return ResponseEntity.ok(new AuthenticationResponse("Successful Signup for client: "+username));
		}
		else{
			return ResponseEntity.ok(new AuthenticationResponse("Error during client signup: "+req.getUsername()));
		}
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "User Login", notes = "Header is not required", response = List.class)
	private ResponseEntity<?> login(@RequestBody AuthenticationRequest req) throws Exception{
		
		String username = req.getUsername();
		String password = req.getPassword();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch(Exception e){
			//throw new Exception("Error during Login for client: "+username);
			return ResponseEntity.ok(new AuthenticationResponse("Error during Login for client: "+username));
		}
		
		UserDetails loadedUser = service.loadUserByUsername(username);
		
		String generatedToken = jwtUtils.generateToken(loadedUser);
		
		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
	}
	
//	private String encodePassword(String pwd){
//		return encoder.encode(pwd);
//	}
}

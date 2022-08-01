package com.cognizant.usermanagement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.usermanagement.model.User;
import com.cognizant.usermanagement.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserManagementApplicationTests {

	@Autowired
	private UserRepository repo;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testLogin(){
		User u = repo.findByUsername("test@test.com");
		if(u != null)
			System.out.println("User exists & can login to MRP application");
		else
			System.out.println("User doesn't have access.. Signup needed");
	}
	
	@Test
	public void testSignup() throws ParseException{
		String dt1="03-10-1997"; 
		DateFormat df = new SimpleDateFormat("dd-MM-yyy");
		Date bDate = df.parse(dt1);
		
		User u = new User();
		u.setFirstName("Revanth");
		u.setLastName("Kumar");
		u.setDob(bDate);
		u.setUsername("abc@def.com");
		u.setPassword("Abcde@1");

		repo.save(u);
	}

}

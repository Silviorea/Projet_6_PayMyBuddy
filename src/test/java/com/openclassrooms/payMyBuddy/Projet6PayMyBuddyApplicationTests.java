package com.openclassrooms.payMyBuddy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.payMyBuddy.model.Roles;
import com.openclassrooms.payMyBuddy.model.User;
import com.openclassrooms.payMyBuddy.repository.UserRepository;
import com.openclassrooms.payMyBuddy.service.OperationService;
import com.openclassrooms.payMyBuddy.service.UserService;
import com.openclassrooms.payMyBuddy.service.UserServiceImpl;

@SpringBootTest
class Projet6PayMyBuddyApplicationTests {

	@InjectMocks
	UserServiceImpl userService;
	
	
	@Mock
	UserRepository userRepo;
	
	User usertest= new User ("TEST", "Test", "emailTest", "passwordTest", Roles.USER);
	User contactTest = new User ("TESTCONTACT", "TestContact", "emailTestContact", "passwordTestContact", Roles.USER);
	
	
	@Test
	void contextLoads() {
	}
	
	
	@Test
	void methodsTest() throws Exception
	{
		
		when(userRepo.findByEmail("emailTestContact")).thenReturn(contactTest);
		
		User test = userRepo.findByEmail("emailTestContact");
		
		assertEquals(test.getEmail(), "emailTestContact");
	}

	

}

package com.vobi.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.UserType;
import com.vobi.bank.domain.Users;
import com.vobi.bank.services.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UserServiceIT {

	@Autowired
	UserService userService;
	
	@Autowired
	UserTypeRepository userTypeRepository; 
	
	@Test
	@Order(1)
	void debeValidarDependencias() {
		assertNotNull(userService);
		assertNotNull(userTypeRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearUnUsuario() throws Exception {
		//Arrange
		Integer idUserType=1;
		String userEmail="test@correo.com";
		
		Users user=null;
		UserType userType=userTypeRepository.findById(idUserType).get();
		
		user=new Users();
		user.setUserEmail(userEmail);
		user.setName("fake name");
		user.setUserType(userType);
		user.setEnable("Y");
		
		//Act
		
		user=userService.save(user);
		
		//Assert		
		assertNotNull(user,"El customer es nulo no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarUnUsuario()throws Exception {
		//Arrange		
		String userEmail="test@correo.com";
		
		Users user=null;		
		
		user= userService.findById(userEmail).get();
		user.setEnable("N");		
		//Act
		
		user=userService.update(user);
		
		//Assert		
		assertNotNull(user,"El usuario es nulo no se pudo modificar");
	}
	
	@Test
	@Order(4)
	void debeEliminarUnUser()throws Exception {
		//Arrange		
		String userEmail="test@correo.com";
		
		Users user=null;		
		Optional<Users> userOptional=null;
		
		user= userService.findById(userEmail).get();		
		//Act
		
		userService.delete(user);
		userOptional = userService.findById(userEmail);
		
		//Assert		
		assertFalse(userOptional.isPresent(),"No pudo borrar al usuario");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosCustomers() {
		//Arrange		
		
		List<Users> users;
		
		users= userService.findAll();		
		//Act  
		users.forEach(user -> log.info(user.getName()));
		//Assert		
		assertFalse(users.isEmpty(),"No pudo consultar los customer");
	}

}

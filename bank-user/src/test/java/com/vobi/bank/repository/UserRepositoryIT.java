package com.vobi.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

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

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UserRepositoryIT {

	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	UserTypeRepository userTypeRepository; 
	
	@Test
	@Order(1)
	void debeValidarDependencias() {
		assertNotNull(userRepository);
		assertNotNull(userTypeRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearunUsuario() {
		//Arrange
		Integer idUserType=1;
		String userEmail= "test@correo.com";
		
		Users user=null;
		UserType userType=userTypeRepository.findById(idUserType).get();
		
		user=new Users();
		user.setUserEmail(userEmail);
		user.setName("fake name");
		user.setUserType(userType);
		user.setEnable("Y");
		
		//Act
		
		user=userRepository.save(user);
		
		//Assert		
		assertNotNull(user,"El user es nulo no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarUnUsuario() {
		//Arrange		
		String userEmail="test@correo.com";
		
		Users user=null;		
		
		user= userRepository.findById(userEmail).get();
		user.setEnable("N");		
		//Act
		
		user=userRepository.save(user);
		
		//Assert		
		assertNotNull(user,"El customer es nulo no se pudo modificar");
	}
	
	@Test
	@Order(4)
	void debeEliminarUnUsuario() {
		//Arrange		
		String userEmail="test@correo.com";
		
		Users user=null;		
			
		Optional<Users> userOptional=null;
		
		user= userRepository.findById(userEmail).get();		
		//Act
		
		userRepository.delete(user);
		userOptional = userRepository.findById(userEmail);
		
		//Assert		
		assertFalse(userOptional.isPresent(),"No pudo borrar al customer");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosUsuarios() {
		//Arrange		
		
		List<Users> users;
		
		users= userRepository.findAll();		
		//Act  
		users.forEach(user -> log.info(user.getName()));
		//Assert		
		assertFalse(users.isEmpty(),"No pudo consultar los users");
	}

}

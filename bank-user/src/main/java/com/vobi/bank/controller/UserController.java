package com.vobi.bank.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vobi.bank.domain.Users;
import com.vobi.bank.dto.UsersDTO;
import com.vobi.bank.mapper.UserMapper;
import com.vobi.bank.services.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	@GetMapping()
	public List<UsersDTO> findAll() throws Exception {
		
		return userMapper.convertListUsersToListUsersDTO(userService.findAll());
	}
	
	@GetMapping("/{email}")
	public UsersDTO findById(@PathVariable("email") String email) throws Exception {
		
		Optional<Users> user = userService.findById(email);
		if(user.isPresent() == false) {
			throw new Exception("User no existe");
		}
		return userMapper.convertUserToUserDTO(user.get());		
	}
	
	@PostMapping()
	public UsersDTO save(@Valid @RequestBody UsersDTO userDTO) throws Exception {
		
		Users user = userMapper.convertUserDTOToUser(userDTO);
		user = userService.save(user);
		return userMapper.convertUserToUserDTO(user);
	}
	
	@PutMapping()
	public UsersDTO update(@Valid @RequestBody UsersDTO userDTO) throws Exception {
		
		Users user = userMapper.convertUserDTOToUser(userDTO);
		user = userService.update(user);
		return userMapper.convertUserToUserDTO(user);
	}
	
	@DeleteMapping("/{email}")
	public void delete(@PathVariable("email") String email) throws Exception {
		userService.deleteById(email);
	}
	
}

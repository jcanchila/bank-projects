package com.vobi.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vobi.bank.domain.Users;
import com.vobi.bank.dto.UsersDTO;

@Mapper
public interface UserMapper {
		
	@Mapping(source= "userType.ustyId" , target = "ustyId")
	UsersDTO convertUserToUserDTO(Users user);
	@Mapping(source= "ustyId", target="userType.ustyId")
	Users convertUserDTOToUser(UsersDTO userDTO);	
	List<UsersDTO> convertListUsersToListUsersDTO(List<Users> users);
	List<Users> convertListUsersDTOToListUsers(List<UsersDTO> usersDTO);
}
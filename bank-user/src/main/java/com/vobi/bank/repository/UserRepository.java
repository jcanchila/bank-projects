package com.vobi.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vobi.bank.domain.Users;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {
	
	List<Users> findByEnable(String enable);
	List<Users> findByName(String name);
	List<Users> findByUserEmail(String userEmail);

}

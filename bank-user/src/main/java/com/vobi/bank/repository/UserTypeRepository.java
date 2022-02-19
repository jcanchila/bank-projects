package com.vobi.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vobi.bank.domain.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

	List<UserType> findByUstyId(Integer ustyId);
	
	List<UserType> findByName(String name);
}

package com.vobi.bank.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.bank.domain.Users;
import com.vobi.bank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Users> findById(String id) {
		return userRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return userRepository.count();
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users save(Users entity) throws Exception {
		if(entity == null) {
			throw new Exception();
		}
		validate(entity);
		
		if(userRepository.existsById(entity.getUserEmail())) {
			throw new Exception("El cliente ya existe");
		}
		
		return userRepository.save(entity);
	}	

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users update(Users entity) throws Exception {
		if(entity == null) {
			throw new Exception();
		}
		validate(entity);
		
		if(userRepository.existsById(entity.getUserEmail())== false) {
			throw new Exception("El cliente no existe");
		}
		
		return userRepository.save(entity);
	}	
	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Users entity) throws Exception {
		if(entity == null) {
			throw new Exception();
		}
		validate(entity);
		
		if(entity.getUserEmail()==null) {
			throw new Exception("El cliente id es nulo");
		}
		if(userRepository.existsById(entity.getUserEmail())== false) {
			throw new Exception("El cliente no existe");
		}
		findById(entity.getUserEmail()).ifPresent(user->{
			if(user.getTransactions() != null && user.getTransactions().isEmpty()== false) {
				throw new RuntimeException("El cliente tiene cuentas asociadas");
			}			
		});
		
		userRepository.deleteById(entity.getUserEmail());
	}	

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		if(id == null) {
			throw new Exception("El id es nulo");
		}
		if(userRepository.existsById(id)== false) {
			throw new Exception("El cliente no existe");
		}
		
		delete(userRepository.findById(id).get());
	}

	@Override
	public void validate(Users entity) throws Exception {
		// TODO Auto-generated method stub
		Set<ConstraintViolation<Users>> constraintValidation = validator.validate(entity);
		if(constraintValidation.isEmpty()== false) {
			throw new ConstraintViolationException(constraintValidation);
		}
		
	}
}

package com.vobi.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/balances")
public class BalanceController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/{id}")
	public BalanceDto findBalance(@PathVariable("id") String id){
		
		String query = String.format("SELECT balance FROM ACCOUNT WHERE acco_id = '%s'", id);
		Double balance = jdbcTemplate.queryForObject(query, Double.class);		
		return new BalanceDto(balance);
	}
	
	@GetMapping("/sayHello/{nombre}")
	public String SayHello(@PathVariable("nombre") String name) {
		return "Hello "+name;
	}
}

class BalanceDto
{
	private Double balance;

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public BalanceDto(Double balance) {
		super();
		this.balance = balance;
	}
	
}

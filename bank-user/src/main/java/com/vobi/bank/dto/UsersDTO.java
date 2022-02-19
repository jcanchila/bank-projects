package com.vobi.bank.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
	@NotNull
	private String userEmail;
	
	@NotNull
	private Integer ustyId;
	
	@NotNull
	private String enable;
	
	private String name;
	
	private String token;
}

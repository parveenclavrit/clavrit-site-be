package com.clavrit.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestDto {

	 private String email;
	    
	 private String name;
	    
	 private String subject;
	 
	 private String message;
	 
	 private String destination;
	 
	 private String company;
	 
	 private String phone;
	 
	 private String country;
	 
}

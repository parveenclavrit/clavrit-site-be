package com.clavrit.Dto;

public class MailRequestDto {

	 private String email;
	    
	 private String name;
	    
	 private String subject;
	 
	 private String message;
	 
	 private String destination;
	 
	 private String company;
	 
	 private String phone;
	 
	 private String country;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public MailRequestDto(String email, String name, String subject, String message, String destination, String company,
			String phone, String country) {
		super();
		this.email = email;
		this.name = name;
		this.subject = subject;
		this.message = message;
		this.destination = destination;
		this.company = company;
		this.phone = phone;
		this.country = country;
	}

	public MailRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	 
}

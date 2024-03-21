package com.eBanckend.dtos;

import lombok.Data;
;


@Data 
public class SavingBankAccounDTO extends BankAccountDTO {
	
	protected String id;
	protected double interestRate;

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	
	
	
		
	}
	

package com.eBanckend.dtos;



import lombok.Data;
@Data
public class CurrentBankAccountDTO  extends BankAccountDTO{
	
	
	
	protected double decouvert;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}


	public double getDecouvert() {
		return decouvert;
	}


	public void setDecouvert(double decouvert) {
		this.decouvert = decouvert;
	}


	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	

}

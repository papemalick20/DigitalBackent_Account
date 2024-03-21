package com.eBanckend.dtos;

import java.util.Date;

import com.eBanckend.enums.AccountStatus;

import lombok.Data;
@Data
public class BankAccountDTO {

	protected String id;
	protected double solde; //balance
	protected Date createdAt;
	protected AccountStatus status;
	protected CustomerDTO customerDTO;
	protected String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}
	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
	

	
	

}

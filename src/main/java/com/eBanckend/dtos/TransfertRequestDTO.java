package com.eBanckend.dtos;



import lombok.Data;

@Data
public class TransfertRequestDTO {
	private String accountIdSource;
	private String accountdestination;
	private double amount;
	private String description;
	public String getAccountIdSource() {
		return accountIdSource;
	}
	public void setAccountIdSource(String accountIdSource) {
		this.accountIdSource = accountIdSource;
	}
	
	public String getAccountdestination() {
		return accountdestination;
	}
	public void setAccountdestination(String accountdestination) {
		this.accountdestination = accountdestination;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}

package com.eBanckend.dtos;

import java.util.Date;


import com.eBanckend.enums.operationType;

public class AccountOperationDTO {
	
	private Long id;
	private Date OperationDate;
	private double amount;
	private operationType Type;
	private String description;
	public void setOperationDate(Date date) {
		this.OperationDate=date;

	}
	public void setAmount(double d) {
		this.amount=d;

	}
	public void setType(operationType type) {
		this.Type=type;

	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getOperationDate() {
		return OperationDate;
	}
	public double getAmount() {
		return amount;
	}
	public operationType getType() {
		return Type;
	}
	


}

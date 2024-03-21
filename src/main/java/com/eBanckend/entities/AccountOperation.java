package com.eBanckend.entities;

import java.util.Date;

import com.eBanckend.enums.operationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date OperationDate;
	private double amount;
	private operationType Type;
	@ManyToOne
	private BankAccount bankAccount;
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
	public void setBankAccount(BankAccount acc) {
		this.bankAccount=acc;

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
	public BankAccount getBankAccount() {
		return bankAccount;
	}

    

}

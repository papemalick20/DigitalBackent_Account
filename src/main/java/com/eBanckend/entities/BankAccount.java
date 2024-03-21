package com.eBanckend.entities;

import java.util.Date;
import java.util.List;

import com.eBanckend.enums.AccountStatus;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", length=4)
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
	@Id
	protected String id;
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	protected double solde; //balance
	protected Date createdAt;
	protected AccountStatus status;
	@ManyToOne
	protected Customer customer;
	@OneToMany(mappedBy="bankAccount", fetch=FetchType.LAZY)
	private List<AccountOperation> accountOperation;
	public void setId(String id) {
		this.id= id;
		
	}
	public void setCreatedAt(Date date) {
		this.createdAt=date;
		
	}
	public void setSolde(double initialSolde) {
		this.solde=initialSolde;
		
	}
	public void setCustomer(Customer customer) {
		this.customer=customer;
		
	}
	public List<AccountOperation> getAccountOperation() {
		return accountOperation;
	}
	public void setAccountOperation(List<AccountOperation> accountOperation) {
		this.accountOperation = accountOperation;
	}
	public String getId() {
		return id;
	}
	public double getSolde() {
		return solde;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Customer getCustomer() {
		return customer;
	}
	

}

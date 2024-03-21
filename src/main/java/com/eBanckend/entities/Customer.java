package com.eBanckend.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private String name;
private String email;
@OneToMany(mappedBy="customer")
@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
private List<BankAccount> bankAccounts;
public void setName(String name) {
	this.name=name;

}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public List<BankAccount> getBankAccounts() {
	return bankAccounts;
}
public void setBankAccounts(List<BankAccount> bankAccounts) {
	this.bankAccounts = bankAccounts;
}
public String getName() {
	return name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email=email;

}
}

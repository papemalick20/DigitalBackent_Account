package com.eBanckend.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoriqueDTO {
    
	private String  accountId;
	private double solde;
	private int currentPage;
	private int totalPage;
	private int sizePage;
	private List<AccountOperationDTO> accountOperationDTOs;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getSizePage() {
		return sizePage;
	}
	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}
	public List<AccountOperationDTO> getAccountOperationDTOs() {
		return accountOperationDTOs;
	}
	public void setAccountOperationDTOs(List<AccountOperationDTO> accountOperationDTOs) {
		this.accountOperationDTOs = accountOperationDTOs;
	}
	
}

package com.eBanckend.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.eBanckend.dtos.AccountOperationDTO;
import com.eBanckend.dtos.CurrentBankAccountDTO;
import com.eBanckend.dtos.CustomerDTO;
import com.eBanckend.dtos.SavingBankAccounDTO;
import com.eBanckend.entities.AccountOperation;
import com.eBanckend.entities.BankAccount;
import com.eBanckend.entities.CurrentAccount;
import com.eBanckend.entities.Customer;
import com.eBanckend.entities.SavingAccount;

@Service
public class BankAccountmapperImpl {

	public CustomerDTO fromCustomer(Customer customer) {
		CustomerDTO customerDTO=new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		return customerDTO;
	}
	 public Customer fromCustomerDTO(CustomerDTO customerDTO) {
		 Customer customer=new Customer();
		 BeanUtils.copyProperties(customerDTO, customer);
		 return customer;
	 }
	 
	 public SavingBankAccounDTO fromSavingBankAccoun(SavingAccount savingAccount) {
		 SavingBankAccounDTO savingBankAccounDTO= new SavingBankAccounDTO();
		 BeanUtils.copyProperties(savingAccount, savingBankAccounDTO);
		 savingBankAccounDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
         savingBankAccounDTO.setType(savingAccount.getClass().getSimpleName());
		return savingBankAccounDTO;
		 
	 }
	  public SavingAccount fromSavingBankAccountDTO(SavingBankAccounDTO savingBankAccounDTO) {
		  SavingAccount savingAccount = new SavingAccount();
		  BeanUtils.copyProperties(savingBankAccounDTO, savingAccount);
		  savingAccount.setCustomer(fromCustomerDTO(savingBankAccounDTO.getCustomerDTO()));
		return savingAccount;
		  
	  }
	  
	  public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
		  CurrentBankAccountDTO currentBankAccountDTO= new CurrentBankAccountDTO();
		  BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
		  currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
		  currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
		return currentBankAccountDTO;
		  
	  }
	   public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
		   CurrentAccount currentAccount = new CurrentAccount();
		   BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
		   currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
		return currentAccount;
		   
	   }
	    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
	    	AccountOperationDTO accountOperationDTO= new AccountOperationDTO();
	    	BeanUtils.copyProperties(accountOperation, accountOperationDTO);
			return accountOperationDTO;
	    }
}

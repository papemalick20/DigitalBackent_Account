package com.eBanckend.services;
import java.util.List;
import java.util.stream.Stream;

import com.eBanckend.entities.BankAccount;
import com.eBanckend.entities.Customer;
import com.eBanckend.exceptions.BankAccountNotFoundException;
import com.eBanckend.exceptions.CustomerNotFoundException;
import com.eBanckend.exceptions.SoldeNotSufficientException;
import com.eBanckend.dtos.AccountHistoriqueDTO;
import com.eBanckend.dtos.AccountOperationDTO;
import com.eBanckend.dtos.BankAccountDTO;
import com.eBanckend.dtos.CurrentBankAccountDTO;
import com.eBanckend.dtos.CustomerDTO;
import com.eBanckend.dtos.SavingBankAccounDTO;
import com.eBanckend.entities.*;
public interface BankAccountService {
	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	CurrentBankAccountDTO saveCurrentBankaccount(double initialSolde, double decouvert, Long customerId) throws CustomerNotFoundException;
	SavingBankAccounDTO saveSavingBankaccount(double initialSolde, double interestRate, Long customerId) throws CustomerNotFoundException;
	List<CustomerDTO> listcustomer( );
	BankAccountDTO getBankaccount(String accountId) throws BankAccountNotFoundException ;
	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, SoldeNotSufficientException;
	void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
	void transfert(String accountIdSource,  double amount, String accountdestination) throws BankAccountNotFoundException, SoldeNotSufficientException;
	List<BankAccountDTO> listBankaccount();
	CustomerDTO getcustomer(Long customerId) throws CustomerNotFoundException;
	CustomerDTO updateCustomer(CustomerDTO customerDTO);
	void deleteCustomer(Long customerId);
	List<AccountOperationDTO> accounthistorique(String accountId);
	AccountHistoriqueDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
	List<CustomerDTO> SearchCustomers(String keyword);
	
	

}
package com.eBanckend.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eBanckend.entities.BankAccount;
import com.eBanckend.dtos.AccountHistoriqueDTO;
import com.eBanckend.dtos.AccountOperationDTO;
import com.eBanckend.dtos.BankAccountDTO;
import com.eBanckend.dtos.CurrentBankAccountDTO;
import com.eBanckend.dtos.CustomerDTO;
import com.eBanckend.dtos.SavingBankAccounDTO;
import com.eBanckend.entities.*;
import com.eBanckend.entities.CurrentAccount;
import com.eBanckend.entities.Customer;
import com.eBanckend.entities.SavingAccount;
import com.eBanckend.enums.operationType;
import com.eBanckend.exceptions.BankAccountNotFoundException;
import com.eBanckend.exceptions.CustomerNotFoundException;
import com.eBanckend.exceptions.SoldeNotSufficientException;
import com.eBanckend.mappers.BankAccountmapperImpl;
import com.eBanckend.repositories.AccountOperationRepository;
import com.eBanckend.repositories.BankAccountRepository;
import com.eBanckend.repositories.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
 
public class BankaccountServiceImpl implements BankAccountService {

	private CustomerRepository customerRepository;
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;
	private BankAccountmapperImpl dtomapper;
	Logger log= LoggerFactory.getLogger(this.getClass().getName());

   public BankaccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository,
			AccountOperationRepository accountOperationRepository, BankAccountmapperImpl dtomapper) {
		super();
		this.customerRepository = customerRepository;
		this.bankAccountRepository = bankAccountRepository;
		this.accountOperationRepository = accountOperationRepository;
		this.dtomapper=dtomapper;
		
	}
        @Override
        public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        	log.info("saving new customer");
		Customer customer= dtomapper.fromCustomerDTO(customerDTO);
		Customer savedCustomer=customerRepository.save(customer);
		return  dtomapper.fromCustomer(savedCustomer);
	  }


	@Override
	public CurrentBankAccountDTO saveCurrentBankaccount(double initialSolde, double decouvert, Long customerId)
			throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null) 
			throw new CustomerNotFoundException("customer not found");
		
		CurrentAccount currentAccount=new CurrentAccount();
		
		
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setCreatedAt(new Date());
		currentAccount.setSolde(initialSolde);
		currentAccount.setDecouvert(decouvert);
		currentAccount.setCustomer(customer);
		CurrentAccount savedBankaccount=bankAccountRepository.save(currentAccount);
		
		return dtomapper.fromCurrentBankAccount(savedBankaccount);
	}


	@Override
	public SavingBankAccounDTO saveSavingBankaccount(double initialSolde, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null) 
			throw new CustomerNotFoundException("customer not found");
		
		SavingAccount savingaccount = new SavingAccount();
		
		
		savingaccount.setId(UUID.randomUUID().toString());
		savingaccount.setCreatedAt(new Date());
		savingaccount.setSolde(initialSolde);
		savingaccount.setInterestRate(interestRate);
		savingaccount.setCustomer(customer);
		SavingAccount savedBankaccount=bankAccountRepository.save(savingaccount);
		
		return dtomapper.fromSavingBankAccoun(savedBankaccount);
		
	}
	


	@Override
	public List<CustomerDTO> listcustomer() {
		
		List<Customer> customers=customerRepository.findAll();
		List<CustomerDTO> customerDTOS=customers.stream().map(customer->dtomapper.fromCustomer(customer)).collect(Collectors.toList());
				
		return customerDTOS;
	}
	@Override
	 public CustomerDTO getcustomer(Long customerId) throws CustomerNotFoundException {
		Customer customer= customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("customer not found"));
		return dtomapper.fromCustomer(customer);
	 }

	@Override
	public BankAccountDTO getBankaccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankaccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()-> new BankAccountNotFoundException("bankaccount not found"));
		if(bankaccount instanceof SavingAccount) {
			SavingAccount savingAccount= (SavingAccount) bankaccount;
			return dtomapper.fromSavingBankAccoun(savingAccount);
		}else {
			CurrentAccount currentAccount= (CurrentAccount) bankaccount;
			return dtomapper.fromCurrentBankAccount(currentAccount);
		}
		
		
	}

	@Override
	public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, SoldeNotSufficientException {
		//Retirer de l'argent das le compte
		
		BankAccount bankaccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()-> new BankAccountNotFoundException("bankaccount not found"));
		if(bankaccount.getSolde()<amount)
			throw new SoldeNotSufficientException("solde not sufficient");
              
		   AccountOperation accountOperation= new AccountOperation();
		   accountOperation.setType(operationType.DEBIT);
		   accountOperation.setAmount(amount);
		   accountOperation.setOperationDate(new Date());
		   accountOperation.setDescription(description);
		   accountOperation.setBankAccount(bankaccount);
		   accountOperationRepository.save(accountOperation);
		   bankaccount.setSolde(bankaccount.getSolde()-amount);
		   bankAccountRepository.save(bankaccount);
	} 

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		// versement de l'argent dans le compte
		
		BankAccount bankaccount=bankAccountRepository.findById(accountId)
				.orElseThrow(()-> new BankAccountNotFoundException("bankaccount not found"));  
		   AccountOperation accountOperation= new AccountOperation();
		   accountOperation.setType(operationType.CREDIT);
		   accountOperation.setAmount(amount);
		   accountOperation.setOperationDate(new Date());
		   accountOperation.setDescription(description);
		   accountOperation.setBankAccount(bankaccount);
		   accountOperationRepository.save(accountOperation);
		   bankaccount.setSolde(bankaccount.getSolde()+amount);
		   bankAccountRepository.save(bankaccount);

	}    

	@Override
	public void transfert(String accountIdSource, double amount,  String accountdestination) throws BankAccountNotFoundException, SoldeNotSufficientException {
		// Transfert
		debit(accountIdSource, amount, accountdestination);
		credit(accountdestination, amount,accountIdSource);

	}
	@Override
	public List<BankAccountDTO> listBankaccount(){
		List<BankAccount> bankAccounts= bankAccountRepository.findAll();
		List<BankAccountDTO> bankAccountDTOs=bankAccounts.stream().map(bankAccount->{
		
			if(bankAccount instanceof SavingAccount) {
				SavingAccount savingAccount= (SavingAccount) bankAccount;
				return dtomapper.fromSavingBankAccoun(savingAccount);
			}else {
				CurrentAccount currentAccount= (CurrentAccount) bankAccount;
				return dtomapper.fromCurrentBankAccount(currentAccount);
			}
		}).collect(Collectors.toList());
		return bankAccountDTOs;
		
	}
	

	
	@Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
    	log.info("saving new customer");
	Customer customer= dtomapper.fromCustomerDTO(customerDTO);
	Customer savedCustomer=customerRepository.save(customer);
	return  dtomapper.fromCustomer(savedCustomer);
  }
     @Override
     public void deleteCustomer(Long customerId) {
    	 customerRepository.deleteById(customerId);
     }
     
     @Override
     public List<AccountOperationDTO> accounthistorique(String accountId){
    	 List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);
    	return accountOperations.stream().map(op->dtomapper.fromAccountOperation(op)).collect(Collectors.toList());
    		 
    	
		
    	 
}
	@Override
	public AccountHistoriqueDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
		BankAccount bankAccount= bankAccountRepository.findById(accountId).orElse(null);
		if(bankAccount==null) throw new BankAccountNotFoundException("bank not found");
	Page<AccountOperation> accountOperations=accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
	List<AccountOperationDTO> accountOperationDTOs= accountOperations.getContent().stream().map(op->dtomapper.fromAccountOperation(op)).collect(Collectors.toList());
	 AccountHistoriqueDTO accountHistoriqueDTO= new AccountHistoriqueDTO();
	 accountHistoriqueDTO.setAccountOperationDTOs(accountOperationDTOs);
	 accountHistoriqueDTO.setAccountId(bankAccount.getId());
	 accountHistoriqueDTO.setSolde(bankAccount.getSolde());
	 accountHistoriqueDTO.setSizePage(size);
	 accountHistoriqueDTO.setCurrentPage(page);
	 accountHistoriqueDTO.setTotalPage(accountOperations.getTotalPages());
		return accountHistoriqueDTO;
	}
	
	@Override
	public List<CustomerDTO> SearchCustomers(String keyword){
		List<Customer> customers=customerRepository.findByNameContains(keyword);
		List<CustomerDTO> customerDTOS=customers.stream().map(cust->dtomapper.fromCustomer(cust)).collect(Collectors.toList());
		return customerDTOS;
		
	}
}
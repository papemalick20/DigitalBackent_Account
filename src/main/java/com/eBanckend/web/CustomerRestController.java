package com.eBanckend.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eBanckend.dtos.AccountHistoriqueDTO;
import com.eBanckend.dtos.AccountOperationDTO;
import com.eBanckend.dtos.BankAccountDTO;
import com.eBanckend.dtos.CreditDTO;
import com.eBanckend.dtos.CustomerDTO;
import com.eBanckend.dtos.DebitDTO;
import com.eBanckend.dtos.TransfertRequestDTO;
import com.eBanckend.exceptions.BankAccountNotFoundException;
import com.eBanckend.exceptions.CustomerNotFoundException;
import com.eBanckend.exceptions.SoldeNotSufficientException;
import com.eBanckend.services.BankAccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
 private BankAccountService bankAccountService;
 
 public CustomerRestController(BankAccountService bankAccountService) {
	super();
	this.bankAccountService = bankAccountService;
}


@GetMapping("/customers")
@PreAuthorize("hasAuthority('SCOPE_USER')")
 public List<CustomerDTO> customers(){
	 return bankAccountService.listcustomer();
 }

@GetMapping("/customers/search")
@PreAuthorize("hasAuthority('SCOPE_USER')")
public List<CustomerDTO> Searchcustomers(@RequestParam(name="keyword", defaultValue="")String keyword){
	 return bankAccountService.SearchCustomers(keyword);
}

@GetMapping("/customers/{id}")
@PreAuthorize("hasAuthority('SCOPE_USER')")
public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
	
	return bankAccountService.getcustomer(customerId);
	
}
@PostMapping("/customers")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
 public CustomerDTO saveCustomer(@RequestBody  CustomerDTO customerDTO) {
	return bankAccountService.saveCustomer(customerDTO);
 }
 @PutMapping("/customers/{customerId}")
 @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
 public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
	 customerDTO.setId(customerId);
	 
	return bankAccountService.updateCustomer(customerDTO);
	 
 }
  @DeleteMapping("/customers/{customerId}")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
   public void deleteCustomer(@PathVariable Long customerId) {
	    bankAccountService.deleteCustomer(customerId);
   }
  @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankaccount(@PathVariable String accountId) throws BankAccountNotFoundException  {
		return bankAccountService.getBankaccount(accountId) ;
	
	  }
      @GetMapping("/accounts")
     public List<BankAccountDTO> lisbAccountDTOs(){
    	 return bankAccountService.listBankaccount();
     }
      @GetMapping("/accounts/{accountId}/operations")
       public List<AccountOperationDTO> getHistorique( @PathVariable String accountId) {
		return bankAccountService.accounthistorique(accountId);
	}
      
      @GetMapping("/accounts/{accountId}/pageOperations")
      public AccountHistoriqueDTO getAccounthistorique(
    		  @PathVariable String accountId, 
    		  @RequestParam(name="page", defaultValue = "0") int page,
    		  @RequestParam(name="size", defaultValue = "5") int size) throws BankAccountNotFoundException  {
		return bankAccountService.getAccountHistory(accountId, page, size);
	}
      @PostMapping("/accounts/debit")
      public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, SoldeNotSufficientException {
    	  this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
		return debitDTO;
		
      }
      
      
      @PostMapping("/accounts/credit")
      public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, SoldeNotSufficientException {
    	  this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
		return creditDTO;
		
      }
      
      @PostMapping("/accounts/transfert")
      public TransfertRequestDTO transfert(@RequestBody TransfertRequestDTO transfertRequestDTO) throws BankAccountNotFoundException, SoldeNotSufficientException {
    	  this.bankAccountService.transfert(
    			  transfertRequestDTO.getAccountdestination(),
    			  transfertRequestDTO.getAmount(),
    			  transfertRequestDTO.getAccountIdSource());
    	  return transfertRequestDTO;
		
		
		
      }

}

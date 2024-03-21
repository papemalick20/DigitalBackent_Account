package com.eBanckend;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eBanckend.dtos.BankAccountDTO;
import com.eBanckend.dtos.CustomerDTO;
import com.eBanckend.entities.AccountOperation;
import com.eBanckend.entities.BankAccount;
import com.eBanckend.entities.CurrentAccount;
import com.eBanckend.entities.Customer;
import com.eBanckend.entities.SavingAccount;
import com.eBanckend.enums.AccountStatus;
import com.eBanckend.enums.operationType;
import com.eBanckend.exceptions.BankAccountNotFoundException;
import com.eBanckend.exceptions.CustomerNotFoundException;
import com.eBanckend.exceptions.SoldeNotSufficientException;
import com.eBanckend.repositories.AccountOperationRepository;
import com.eBanckend.repositories.BankAccountRepository;
import com.eBanckend.repositories.CustomerRepository;
import com.eBanckend.services.BankAccountService;

@SpringBootApplication
public class EBackendBankingApplication {


	public static void main(String[] args) {
		SpringApplication.run(EBackendBankingApplication.class, args);

	}

    @Bean
    CommandLineRunner commandeLineRunner(BankAccountService bankAccountService) {
		return (args)->{
			Stream.of("Hassan", "Ahmed", "Seynabou").forEach(name->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listcustomer().forEach(customer->{
				try {
					bankAccountService.saveCurrentBankaccount(Math.random()*9000, 9000, customer.getId());
					bankAccountService.saveSavingBankaccount(Math.random()*12000, 5.5, customer.getId());
					
					
				} catch (CustomerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 			
				});
			
			List<BankAccountDTO> listbankAccounts= bankAccountService.listBankaccount();
			for(BankAccountDTO bankAccount: listbankAccounts) {
				for(int i=0; i<10; i++) {
					bankAccountService.credit(bankAccount.getId(), 10000+Math.random(), "credit");
					bankAccountService.debit(bankAccount.getId(), 1000+Math.random(), "debit");
				}
			}
		};
	}

	
	
	
	
	
	 }
	


	




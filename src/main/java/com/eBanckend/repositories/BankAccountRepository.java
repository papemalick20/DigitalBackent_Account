package com.eBanckend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eBanckend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

}

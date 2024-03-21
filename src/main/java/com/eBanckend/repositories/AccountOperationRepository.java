package com.eBanckend.repositories;

import java.util.List;


import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eBanckend.entities.AccountOperation;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long>{

	List<AccountOperation> findByBankAccountId(String accountId);
	Page<AccountOperation> findByBankAccountId(String accountId, PageRequest pageRequest);
 

}

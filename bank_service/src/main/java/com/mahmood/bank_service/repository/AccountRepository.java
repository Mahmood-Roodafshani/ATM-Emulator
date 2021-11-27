package com.mahmood.bank_service.repository;

import com.mahmood.bank_service.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

package com.mahmood.bank_service.repository;

import com.mahmood.bank_service.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}

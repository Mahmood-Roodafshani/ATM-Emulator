package com.mahmood.bank_service.repository;

import com.mahmood.bank_service.entities.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogHistoryRepository extends JpaRepository<LogHistory, Long> {
}

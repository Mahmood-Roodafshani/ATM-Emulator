package com.mahmood.bank_service.aspect;

import com.mahmood.bank_service.entities.LogHistory;
import com.mahmood.bank_service.repository.LogHistoryRepository;
import enums.OperationType;
import lombok.RequiredArgsConstructor;
import model.TransactionModel;
import model.TransactionResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogHistoryRepository logHistoryRepository;

    @AfterReturning(pointcut = "execution(* com.mahmood.bank_service.service.impl.AccountServiceImpl.withdraw(..)) && args(transactionModel,..)",
            returning = "retVal")
    public void logExecutionTimeWithdraw(JoinPoint joinPoint, TransactionModel transactionModel, TransactionResult retVal) throws Throwable {

        LogHistory logHistory = new LogHistory();
        logHistory.setCardNumber(transactionModel.getCardNumber());
        logHistory.setOperationType(OperationType.WITHDRAW);
        logHistory.setAmount(transactionModel.getAmount());
        logHistory.setDateTime(new Date());
        logHistory.setOperationStatus(retVal.getResultCode());
        logHistoryRepository.save(logHistory);
    }

    @AfterReturning(pointcut = "execution(* com.mahmood.bank_service.service.impl.AccountServiceImpl.deposit(..)) && args(transactionModel,..)",
            returning = "retVal")
    public void logExecutionTimeDeposit(JoinPoint joinPoint, TransactionModel transactionModel, TransactionResult retVal) throws Throwable {

        LogHistory logHistory = new LogHistory();
        logHistory.setCardNumber(transactionModel.getCardNumber());
        logHistory.setOperationType(OperationType.DEPOSIT);
        logHistory.setAmount(transactionModel.getAmount());
        logHistory.setDateTime(new Date());
        logHistory.setOperationStatus(retVal.getResultCode());
        logHistoryRepository.save(logHistory);
    }

    @AfterReturning(pointcut = "execution(* com.mahmood.bank_service.service.impl.AccountServiceImpl.checkBalance(..)) && args(accountNumber,..)",
            returning = "retVal")
    public void logExecutionTimeCheckBalance(JoinPoint joinPoint, String accountNumber, TransactionResult retVal) throws Throwable {

        LogHistory logHistory = new LogHistory();
        logHistory.setCardNumber(accountNumber);
        logHistory.setOperationType(OperationType.CHECK_BALANCE);
        logHistory.setAmount(null);
        logHistory.setDateTime(new Date());
        logHistory.setOperationStatus(retVal.getResultCode());
        logHistoryRepository.save(logHistory);
    }
}

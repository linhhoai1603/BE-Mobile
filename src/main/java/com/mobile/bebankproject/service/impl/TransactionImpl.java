package com.mobile.bebankproject.service.impl;

import com.mobile.bebankproject.model.Account;
import com.mobile.bebankproject.model.Transaction;
import com.mobile.bebankproject.repository.AccountRepository;
import com.mobile.bebankproject.repository.TransactionRepository;
import com.mobile.bebankproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Transaction> getTransactionByAccountId(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionRepository.findByAccountOrderByTransactionDateDesc(account);
    }

    @Override
    public List<Transaction> getTransactionByAccountIdAndByMonth_Year(Integer accountId, int month, int year) {
        return transactionRepository.findByAccountIdAndMonthYear(accountId, month, year);
    }
}

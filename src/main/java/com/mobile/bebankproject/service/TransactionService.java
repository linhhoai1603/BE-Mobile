package com.mobile.bebankproject.service;

import com.mobile.bebankproject.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionByAccountId(Integer accountId);
    List<Transaction> getTransactionByAccountIdAndByMonth_Year(Integer accountId, int month, int year);
}

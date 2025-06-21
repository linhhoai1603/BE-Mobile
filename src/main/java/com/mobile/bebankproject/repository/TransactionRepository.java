package com.mobile.bebankproject.repository;

import com.mobile.bebankproject.model.Transaction;
import com.mobile.bebankproject.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND " +
           "YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountIdAndMonthYear(
        @Param("accountId") Integer accountId,
        @Param("month") int month,
        @Param("year") int year
    );
} 
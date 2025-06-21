package com.mobile.bebankproject.model;

import com.mobile.bebankproject.dto.TransactionResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String fromAccount;
    LocalDateTime transactionDate;
    String description;
    double amount;
    @Enumerated(EnumType.STRING)
    TransactionStatus status;
    @ManyToOne @JoinColumn(name = "account_id")
    Account account;

    public TransactionResponse convertToResponse() {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountId(this.account.getId());
        transactionResponse.setTransactionDate(this.transactionDate);
        transactionResponse.setDescription(this.description);
        transactionResponse.setIdTransaction(this.id);
        transactionResponse.setStatus(this.status.toString());
        transactionResponse.setAmount(this.getAmount());
        
        // Xác định loại transaction
        if (this instanceof TransactionFundTransfer) {
            transactionResponse.setType("FUND TRANSFER");
            TransactionFundTransfer fundTransfer = (TransactionFundTransfer) this;
            transactionResponse.setToAccountNumber(fundTransfer.getToAccount().getAccountNumber());
        } else if (this instanceof TransactionBillPayment) {
            transactionResponse.setType("BILL PAYMENT");
            TransactionBillPayment billPayment = (TransactionBillPayment) this;
            transactionResponse.setBillCode(billPayment.getBillCode());
            transactionResponse.setBillType(billPayment.getBillType().toString());
        } else if (this instanceof TransactionLoan) {
            transactionResponse.setType("LOAN");
            TransactionLoan loan = (TransactionLoan) this;
            transactionResponse.setLoanId(loan.getLoan().getId());
        } else if (this instanceof RechargeService) {
            transactionResponse.setType("RECHARGE");
            RechargeService recharge = (RechargeService) this;
            transactionResponse.setPhoneNumber(recharge.getPhoneNumber());
            transactionResponse.setTelcoProvider(recharge.getTelcoProvider().toString());
        } else if (this instanceof DataMobileService) {
            transactionResponse.setType("DATA MOBILE");
            DataMobileService dataService = (DataMobileService) this;
            transactionResponse.setPhoneNumber(dataService.getPhoneNumber());
            transactionResponse.setTelcoProvider(dataService.getTelcoProvider().toString());
        } else {
            transactionResponse.setType("UNKNOWN");
        }
        
        return transactionResponse;
    }
}

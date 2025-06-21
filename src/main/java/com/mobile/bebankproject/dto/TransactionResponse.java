package com.mobile.bebankproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private int accountId;
    private double amount;
    private int idTransaction;
    private LocalDateTime transactionDate;
    private String type;
    private String description;
    private String status;
    
    // Thêm các trường mới
    private String toAccountNumber;  // Cho giao dịch chuyển tiền
    private String billCode;         // Cho giao dịch thanh toán hóa đơn
    private String billType;         // Cho giao dịch thanh toán hóa đơn
    private Long loanId;             // Cho giao dịch vay
    private String phoneNumber;      // Cho giao dịch nạp tiền và data
    private String telcoProvider;    // Cho giao dịch nạp tiền và data
}

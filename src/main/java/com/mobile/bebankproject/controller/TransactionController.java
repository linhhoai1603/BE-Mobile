package com.mobile.bebankproject.controller;

import com.mobile.bebankproject.dto.TransactionResponse;
import com.mobile.bebankproject.model.Transaction;
import com.mobile.bebankproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Lấy danh sách giao dịch của một tài khoản
     * @param accountId ID của tài khoản
     * @return Danh sách giao dịch
     */
    @PostMapping("/account/{accountId}")
    public ResponseEntity<?> getTransactionsByAccount(@PathVariable Integer accountId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionByAccountId(accountId);
            List<TransactionResponse> responses = transactions.stream()
                    .map(t -> {
                        TransactionResponse response = t.convertToResponse();
                        // Nếu fromAccount khác accountId hiện tại, đây là giao dịch nhận tiền
                        if (t.getFromAccount() != null && !t.getFromAccount().equals(accountId.toString())) {
                            response.setType("RECEIVED");
                        }
                        return response;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Lấy danh sách giao dịch của một tài khoản theo tháng và năm
     * @param accountId ID của tài khoản
     * @param month Tháng (1-12)
     * @param year Năm
     * @return Danh sách giao dịch
     */
    @GetMapping("/account/{accountId}/month/{month}/year/{year}")
    public ResponseEntity<?> getTransactionsByMonthYear(
            @PathVariable Integer accountId,
            @PathVariable int month,
            @PathVariable int year) {
        try {
            // Validate month
            if (month < 1 || month > 12) {
                return ResponseEntity.badRequest().body("Month must be between 1 and 12");
            }

            List<Transaction> transactions = transactionService.getTransactionByAccountIdAndByMonth_Year(accountId, month, year);
            List<TransactionResponse> responses = transactions.stream()
                    .map(t -> {
                        TransactionResponse response = t.convertToResponse();
                        // Nếu fromAccount khác accountId hiện tại, đây là giao dịch nhận tiền
                        if (t.getFromAccount() != null && !t.getFromAccount().equals(accountId.toString())) {
                            response.setType("RECEIVED");
                        }
                        return response;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

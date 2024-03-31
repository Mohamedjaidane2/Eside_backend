package com.eside.payment.controller;

import com.eside.payment.dto.TransactionDtos.TransactionDto;
import com.eside.payment.dto.TransactionDtos.TransactionNewDto;
import com.eside.payment.service.ITransactionService;
import com.eside.payment.utils.SuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api("/transactions")
@RequestMapping("/api/transactions")
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final ITransactionService transactionService;

    @PostMapping("/create")
    //@ApiOperation(value = "Create transaction")
    public ResponseEntity<SuccessDto> createTransaction(@RequestBody TransactionNewDto transactionNewDto) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionNewDto));
    }

    @GetMapping("/{transactionId}")
    //@ApiOperation(value = "Get transaction by ID")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long transactionId) {
        TransactionDto transactionDto = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping("/user/{userId}")
    //@ApiOperation(value = "Get transactions by user")
    public ResponseEntity<List<TransactionDto>> getTransactionsByWalletID(@PathVariable Long walletId) {
        List<TransactionDto> transactions = transactionService.getTransactionsByWalletID(walletId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/all")
    //@ApiOperation(value = "Get all transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/cancel/{transactionId}")
    //@ApiOperation(value = "Cancel transaction by ID")
    public ResponseEntity<SuccessDto> cancelTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.cancelTransaction(transactionId));
    }
}

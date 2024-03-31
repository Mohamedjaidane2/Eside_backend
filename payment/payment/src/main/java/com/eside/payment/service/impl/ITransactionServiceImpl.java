package com.eside.payment.service.impl;

import com.eside.payment.dto.TransactionDtos.TransactionDto;
import com.eside.payment.dto.TransactionDtos.TransactionNewDto;
import com.eside.payment.exception.EntityNotFoundException;
import com.eside.payment.model.Transaction;
import com.eside.payment.model.Wallet;
import com.eside.payment.repository.TransactionRepository;
import com.eside.payment.repository.WalletRepository;
import com.eside.payment.service.ITransactionService;
import com.eside.payment.utils.SuccessDto;
import com.eside.payment.utils.SuccessMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ITransactionServiceImpl implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository iWalletRepository;

    @Override
    public SuccessDto createTransaction(TransactionNewDto transactionNewDto) {
        Wallet wallet = iWalletRepository.findById(transactionNewDto.getWalletId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        Transaction transaction = Transaction.builder()
                .transaction_date(new Date())
                .balanceBeforeAction(transactionNewDto.getBalanceBeforeAction())
                .current_balance(transactionNewDto.getCurrent_balance())
                .wallet(wallet)
                .amount(transactionNewDto.getAmount())
                .action(transactionNewDto.getAction())
                .is_canceled(false)
                .build();


        transactionRepository.save(transaction);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public TransactionDto getTransactionById(Long transactionId) {
        // Retrieve the Transaction entity by its ID
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        return TransactionDto.customMapping(transaction);
    }

    @Override
    public List<TransactionDto> getTransactionsByWalletID(Long WalletId) {
        Wallet wallet = iWalletRepository.findById(WalletId)
                .orElseThrow((() -> new EntityNotFoundException("Transaction not found")));
        return TransactionDto.customListMapping(wallet.getTransactions());
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto cancelTransaction(Long transactionId) {
        // Retrieve the Transaction entity by its ID
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        // Perform the cancellation logic and update the transaction status
        transaction.set_canceled(true);
        transactionRepository.save(transaction);

        return SuccessDto.builder()
                .message(SuccessMessage.STATUS_CHANGED)
                .build();
    }
}
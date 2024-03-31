package com.eside.payment.service;

import com.eside.payment.dto.TransactionDtos.TransactionDto;
import com.eside.payment.dto.TransactionDtos.TransactionNewDto;
import com.eside.payment.utils.SuccessDto;

import java.util.List;

public interface ITransactionService {

    SuccessDto createTransaction(TransactionNewDto transactionNewDto);

    TransactionDto getTransactionById(Long transactionId);

    List<TransactionDto> getTransactionsByWalletID(Long WalletId);

    List<TransactionDto> getAllTransactions();

    SuccessDto cancelTransaction(Long transactionId);

}

package com.eside.payment.service.impl;

import com.eside.payment.client.AccountClient;
import com.eside.payment.dto.TransactionDtos.TransactionNewDto;
import com.eside.payment.dto.WalletDtos.WalletDto;
import com.eside.payment.dto.WalletDtos.WalletNewDto;
import com.eside.payment.exception.EntityNotFoundException;
import com.eside.payment.exception.InvalidOperationException;
import com.eside.payment.externalData.Account;
import com.eside.payment.model.Wallet;
import com.eside.payment.repository.WalletRepository;
import com.eside.payment.service.ITransactionService;
import com.eside.payment.service.IWalletService;
import com.eside.payment.utils.SuccessDto;
import com.eside.payment.utils.SuccessMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    //private final IAccountRepository iAccountRepository;
    private final WalletRepository iWalletRepository;
    private final ITransactionService iTransactionServices;

    @Autowired
    private AccountClient accountClient;


    @Override
    public SuccessDto createWallet(@NotNull WalletNewDto walletNewDto) {
        //TODO Throw exeption when account id is duplicated --> throw Already USED


        //Account account = iAccountRepository.findById(walletNewDto.getAccountId())
        //        .orElseThrow(() -> new EntityNotFoundException("Account not found", ErrorCodes.ACCOUNT_NOT_FOUND));

        Optional<Wallet> wallet = iWalletRepository.findByAccountId(walletNewDto.getAccountId());
        if(wallet.isPresent()){
            throw new InvalidOperationException("Wallet already exist");
        }
        try {
            Account account = accountClient.getAccountByIdFromPayment(walletNewDto.getAccountId());
        }catch (EntityNotFoundException e){
            throw e ;
        }

        // Create a new Wallet entity with default balance
        Wallet newwallet = Wallet.builder()
                .accountId(walletNewDto.getAccountId())
                .balance(0.00)
                .build();
        // Save the wallet to the database
        iWalletRepository.save(newwallet);

        // Create a SuccessDto with a success message
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public double getWalletBalance(Long walletId) {
        // Retrieve the Wallet entity or throw an exception if not found
        Wallet wallet = iWalletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        // Get the balance from the Wallet entity and return it
        return wallet.getBalance();
    }

    @Override
    public WalletDto getWalletById(Long walletId) {
        Wallet wallet = iWalletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));
        return WalletDto.customMapping(wallet);
    }

    @Override
    public SuccessDto addFundsToWallet(Long walletId, double amount) {
        if (amount<0){
            throw new InvalidOperationException("amount must be superior then zero");
        }

        Wallet wallet = iWalletRepository.findById(walletId)
                .orElseThrow((() -> new EntityNotFoundException("Wallet not found")));


        double balance = wallet.getBalance();
        double newBalance = balance + amount;
        String transactionDetails = balance + " + " + amount + " = " + newBalance;


        TransactionNewDto transactionNewDto = TransactionNewDto.builder()
                .amount(amount)
                .transaction_date(new Date())
                .current_balance(newBalance)
                .balanceBeforeAction(balance)
                .action(transactionDetails)
                .walletId(walletId)
                .build();
        try {
            iTransactionServices.createTransaction(transactionNewDto);
        } catch (InvalidOperationException e) {
            throw new InvalidOperationException("Transaction Not valid");
        }
        wallet.setBalance(wallet.getBalance() + amount);
        iWalletRepository.save(wallet);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public SuccessDto withdrawFunds(Long walletId, double amount) {
        Wallet wallet = iWalletRepository.findById(walletId)
                .orElseThrow((() -> new EntityNotFoundException("Wallett not found")));
        if(amount>wallet.getBalance()){
            throw new InvalidOperationException("Insuffisant funds");
        }
        double balance = wallet.getBalance();
        double newBalance = balance - amount;
        String transactionDetails = balance + " - " + amount + " = " + newBalance;

        TransactionNewDto transactionNewDto = TransactionNewDto.builder()
                .amount(amount)
                .transaction_date(new Date())
                .current_balance(newBalance)
                .balanceBeforeAction(balance)
                .action(transactionDetails)
                .walletId(walletId)
                .build();
        wallet.setBalance(wallet.getBalance() - amount);
        try {
            iTransactionServices.createTransaction(transactionNewDto);
        } catch (InvalidOperationException e) {
            throw new InvalidOperationException("Transaction Noy valid");
        }
        iWalletRepository.save(wallet);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public WalletDto getWalletByAccountId(Long id) {
        Wallet wallet = iWalletRepository.findByAccountId(id)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));
        return WalletDto.customMapping(wallet);
    }
}

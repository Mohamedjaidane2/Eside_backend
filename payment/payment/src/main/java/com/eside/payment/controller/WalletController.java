package com.eside.payment.controller;

import com.eside.payment.dto.WalletDtos.WalletDto;
import com.eside.payment.dto.WalletDtos.WalletNewDto;
import com.eside.payment.service.IWalletService;
import com.eside.payment.utils.SuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Api("/wallets")
@RequestMapping("/api/wallet")
@RestController
@RequiredArgsConstructor
public class WalletController {
    private final IWalletService walletService;

    @PostMapping("/create")
    //@ApiOperation("Create a wallet ")
    public ResponseEntity<SuccessDto> createWallet(@RequestBody WalletNewDto walletNewDto) {
        return ResponseEntity.ok(walletService.createWallet(walletNewDto));
    }

    @GetMapping("/balance/{walletId}")
    //@ApiOperation("Get the balance by walletId")
    public ResponseEntity<Double> getWalletBalance(@PathVariable Long walletId) {
        double balance = walletService.getWalletBalance(walletId);
        return ResponseEntity.ok(balance);
    }
    @GetMapping("/get/{walletId}")
    //@ApiOperation("Get the Wallet by walletId")
    public ResponseEntity<WalletDto> getWalletById(@PathVariable Long walletId) {
        WalletDto wallet = walletService.getWalletById(walletId);
        return ResponseEntity.ok(wallet);
    }
    @GetMapping("/getbyaccount/{accountId}")
    //@ApiOperation("Get the Wallet by walletId")
    public ResponseEntity<WalletDto> getWalletByAccountId(@PathVariable Long accountId) {
        WalletDto wallet = walletService.getWalletByAccountId(accountId);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/add-funds")
    //@ApiOperation("Add funds to a wallet")
    public ResponseEntity<SuccessDto> addFundsToWallet(@RequestParam Long walletId, @RequestParam double amount) {
        return ResponseEntity.ok(walletService.addFundsToWallet(walletId, amount));
    }

    @PostMapping("/withdraw-funds")
    //@ApiOperation("Withdraw funds from a wallet")
    public ResponseEntity<SuccessDto> withdrawFunds(@RequestParam Long walletId, @RequestParam double amount) {
        return ResponseEntity.ok(walletService.withdrawFunds(walletId, amount));
    }
}


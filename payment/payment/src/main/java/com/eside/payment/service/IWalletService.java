package com.eside.payment.service;

import com.eside.payment.dto.WalletDtos.WalletDto;
import com.eside.payment.dto.WalletDtos.WalletNewDto;
import com.eside.payment.utils.SuccessDto;

public interface IWalletService{

    SuccessDto createWallet(WalletNewDto walletNewDto);

    double getWalletBalance(Long walletId);
    WalletDto getWalletById(Long walletId);

    SuccessDto addFundsToWallet(Long walletId, double amount);

    SuccessDto withdrawFunds(Long  walletId, double amount);

    WalletDto getWalletByAccountId(Long id );
}

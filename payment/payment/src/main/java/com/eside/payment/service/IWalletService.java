package com.eside.payment.service;

import com.eside.payment.dto.WalletDtos.WalletActionDto;
import com.eside.payment.dto.WalletDtos.WalletDto;
import com.eside.payment.dto.WalletDtos.WalletNewDto;
import com.eside.payment.utils.SuccessDto;

public interface IWalletService{

    WalletDto createWallet(WalletNewDto walletNewDto);

    double getWalletBalance(Long walletId);
    WalletDto getWalletById(Long walletId);

    SuccessDto addFundsToWallet(WalletActionDto walletActionDto);

    SuccessDto withdrawFunds(WalletActionDto walletActionDto);

    WalletDto getWalletByAccountId(Long id );
}

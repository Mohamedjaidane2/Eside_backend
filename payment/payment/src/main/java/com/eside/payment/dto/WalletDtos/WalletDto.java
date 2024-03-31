package com.eside.payment.dto.WalletDtos;

import com.eside.payment.dto.TransactionDtos.TransactionDto;
import com.eside.payment.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WalletDto {

    private Long id;

    private Double balance;

    private Long accountId;

    private List<TransactionDto> transactions;


    public static WalletDto customMapping (Wallet wallet){
        return WalletDto.builder()
                .id(wallet.getWallet_id())
                .balance(wallet.getBalance())
                .accountId(wallet.getAccountId())
                .transactions(TransactionDto.customListMapping(wallet.getTransactions()))
                .build();
    }

}

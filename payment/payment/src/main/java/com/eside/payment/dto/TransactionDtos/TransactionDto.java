package com.eside.payment.dto.TransactionDtos;

import com.eside.payment.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionDto {
    private Long id;

    private Date transaction_date;

    private String action ;

    private Long walletId;

    private double amount;

    private double balanceBeforeAction;

    private double current_balance;

    private boolean is_canceled;

    public static List<TransactionDto> customListMapping(List<Transaction> transactions) {
        if(transactions==null) return null;
        ArrayList<TransactionDto> transactionDtoArrayList = new ArrayList<>();
        for (Transaction transaction : transactions){
            TransactionDto transactionDto = customMapping(transaction);
            transactionDtoArrayList.add(transactionDto);
        }
        return transactionDtoArrayList;
    }

    public static TransactionDto customMapping (Transaction transaction){
        return TransactionDto.builder()
                .id(transaction.getId())
                .transaction_date(transaction.getTransaction_date())
                .action(transaction.getAction())
                .walletId(transaction.getWallet().getWallet_id())
                .amount(transaction.getAmount())
                .balanceBeforeAction(transaction.getBalanceBeforeAction())
                .current_balance(transaction.getCurrent_balance())
                .is_canceled(transaction.is_canceled())
                .build();
    }
}

package com.eside.Order.externalData;

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

}

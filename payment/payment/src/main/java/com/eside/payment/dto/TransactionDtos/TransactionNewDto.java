package com.eside.payment.dto.TransactionDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionNewDto {

    private Date transaction_date;

    private Long walletId;

    private Double amount;

    private String action ;

    private Double balanceBeforeAction;

    private Double current_balance;
}

package com.eside.payment.dto.WalletDtos;

import com.eside.payment.dto.TransactionDtos.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WalletUpdateDto {

    private Float balance;

    private List<TransactionDto> transactions;
}

package com.eside.payment.dto.BankDataDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BankDataNewDto {

    private String bankName;

    private String rib;

    private Long wallet_id;
}

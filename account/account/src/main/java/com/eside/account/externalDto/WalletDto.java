package com.eside.account.externalDto;

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

}

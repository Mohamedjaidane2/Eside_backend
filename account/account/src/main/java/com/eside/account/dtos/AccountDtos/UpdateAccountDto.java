package com.eside.account.dtos.AccountDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateAccountDto {
    private String firstName;
    private String LastName;
}


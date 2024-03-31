package com.eside.account.dtos.AccountDtos;

import com.eside.account.model.Account;
import com.eside.account.model.Information;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountDto {

    private Long id;
    private String accountName;
    private Date createdAt;
    private Date updateAt;
    private boolean isActive;
    private String firstName;
    private String lastName;

    public static AccountDto customMapping(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .accountName(account.getAccountName())
                .createdAt(account.getCreationDate())
                .updateAt(account.getUpdateDate())
                .isActive(account.isActive())
                .build();
    }
}
package com.eside.account.service;

import com.eside.account.dtos.AccountDtos.AccountDto;
import com.eside.account.dtos.AccountDtos.NewAccountDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(NewAccountDto newAccountDto);
    SuccessDto updateAccountName(Long id , String newAccountName);
    SuccessDto deactivateAccountById(Long id);
    SuccessDto ReactivateAccountById(Long id);
    //AccountDto getAccountByUserEmail (User user);
    AccountDto getAccountById(Long id);
    List<AccountDto> getAllAccounts ();

}

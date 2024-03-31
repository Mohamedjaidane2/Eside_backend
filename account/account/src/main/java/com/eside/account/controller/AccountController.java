package com.eside.account.controller;


import com.eside.account.dtos.AccountDtos.AccountDto;
import com.eside.account.dtos.AccountDtos.NewAccountDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.model.Account;
import com.eside.account.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api("/account")
@RequestMapping("/api/account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping("/create")
    //@ApiOperation(value = "Create account")
    public ResponseEntity<Account> create (@RequestBody NewAccountDto newAccountDto) {
        return ResponseEntity.ok(accountService.createAccount(newAccountDto));
    }
    @PutMapping("/update")
    //@ApiOperation(value = "Update account name")
    public ResponseEntity<SuccessDto> updateAccountName(@RequestBody Long id , @RequestBody String newAccountName) {
        return ResponseEntity.ok(accountService.updateAccountName(id,newAccountName));
    }

    @PostMapping("/deactivate")
    //@ApiOperation(value = "Deactivate account")
    public ResponseEntity<SuccessDto> deactivateAccount(@RequestBody Long id) {
        return ResponseEntity.ok(accountService.deactivateAccountById(id));
    }
    @PostMapping("/activate")
    //@ApiOperation(value = "activate account")
    public ResponseEntity<SuccessDto> activateAccount(@RequestBody Long id) {
        return ResponseEntity.ok(accountService.ReactivateAccountById(id));
    }

    @PostMapping("/get/id")
    //@ApiOperation(value = "Get account by id")
    public ResponseEntity<AccountDto> getById(@RequestBody Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/get/all")
    //@ApiOperation(value = "Get all account ")
    public ResponseEntity<List<AccountDto>> getAll() {
        List<AccountDto> allAccounts = accountService.getAllAccounts();
        return ResponseEntity.ok(allAccounts);
    }

}
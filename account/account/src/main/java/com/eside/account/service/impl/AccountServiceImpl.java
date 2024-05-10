package com.eside.account.service.impl;

import com.eside.account.client.WalletClient;
import com.eside.account.dtos.AccountDtos.AccountDto;
import com.eside.account.dtos.AccountDtos.NewAccountDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.exception.EntityNotFoundException;
import com.eside.account.exception.InvalidOperationException;
import com.eside.account.externalDto.WalletDto;
import com.eside.account.externalDto.WalletNewDto;
import com.eside.account.model.Account;
import com.eside.account.model.Information;
import com.eside.account.repository.AccountRepository;
import com.eside.account.repository.InformationRepository;
import com.eside.account.service.AccountService;
import com.eside.account.utils.SuccessMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final InformationRepository informationRepository;
    private final WalletClient walletClient;
    @Override
    @Transactional
    public Account createAccount(NewAccountDto newAccountDto) {
        String accountName = generateUniqueAccountName(newAccountDto.getFirstName(), newAccountDto.getLastName());
        Account newAccount = buildAccount(newAccountDto, accountName);
        Information emptyInfo = createEmptyInformation(newAccount);
        return newAccount;
    }

    private Account buildAccount(NewAccountDto newAccountDto, String accountName) {
        return Account.builder()
                .accountName(accountName)
                .creationDate(new Date())
                .isActive(true)
                .firstName(newAccountDto.getFirstName())
                .lastName(newAccountDto.getLastName())
                .build();
    }

    private Information createEmptyInformation(Account newAccount) {
        Information emptyInfo = Information.builder()
                .city("")
                .account(newAccount)
                .address("")
                .bio("")
                .creationDate(new Date())
                .phoneNumber("")
                .optionalAddress("")
                .profilePicture("")
                .build();

        newAccount.setInformation(emptyInfo);

        accountRepository.save(newAccount);
        informationRepository.save(emptyInfo);
        WalletDto walletDto = walletClient.createWallet(WalletNewDto.builder().AccountId(newAccount.getId()).build());
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(walletDto);
        return emptyInfo;
    }

    private String generateUniqueAccountName(String firstName, String lastName) {
        String baseAccountName = firstName + "_" + lastName;
        String uniqueAccountName = baseAccountName;
        int suffix = 1;
        while (accountRepository.findByAccountName(uniqueAccountName).isPresent()) {
            uniqueAccountName = baseAccountName + suffix;
            suffix++;
        }
        return uniqueAccountName;
    }

    @Override
    public SuccessDto updateAccountName(Long id, String newAccountName) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("account not found"));
        Optional<Account> accountCheckAvalibilty = accountRepository.findByAccountName(newAccountName);
        if (accountCheckAvalibilty.isPresent()){
            throw new InvalidOperationException("account with this name alredy exist");
        }
        account.setAccountName(newAccountName);
        account.setUpdateDate(new Date());
        accountRepository.save(account);
        return SuccessDto.builder().message(SuccessMessage.SUCCESSFULLY_UPDATED).build();
    }
    @Override
    public SuccessDto deactivateAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("account not found"));
        account.setActive(false);
        accountRepository.save(account);
        return SuccessDto.builder().message(SuccessMessage.SUCCESSFULLY_DISABLED).build();
    }
    @Override
    public SuccessDto ReactivateAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        account.setActive(true);
        accountRepository.save(account);
        return SuccessDto.builder().message(SuccessMessage.SUCCESSFULLY_ACTIVATED).build();
    }
    @Override
    public AccountDto getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }
        return AccountDto.customMapping(account.get());
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDto::customMapping)
                .collect(Collectors.toList());
    }
}

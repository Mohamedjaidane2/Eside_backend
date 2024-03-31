package com.eside.payment.service;

import com.eside.payment.dto.BankDataDtos.BankDataDto;
import com.eside.payment.dto.BankDataDtos.BankDataNewDto;
import com.eside.payment.dto.BankDataDtos.BankDataUpdateDto;
import com.eside.payment.utils.SuccessDto;

import java.util.List;

public interface IBankDataService {
    SuccessDto addBankData(BankDataNewDto bankDataNewDto);

    SuccessDto updateBankData(BankDataUpdateDto bankDataUpdateDto);


    BankDataDto getBankDataById(Long bankDataId);

//    BankDataDto getBankDataByAccount(AccountDto accountDto);

    List<BankDataDto> getAllBankData();

    SuccessDto deleteBankDataById(Long bankDataId);
}

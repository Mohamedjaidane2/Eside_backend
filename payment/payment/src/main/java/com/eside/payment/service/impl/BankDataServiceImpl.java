package com.eside.payment.service.impl;

import com.eside.payment.dto.BankDataDtos.BankDataDto;
import com.eside.payment.dto.BankDataDtos.BankDataNewDto;
import com.eside.payment.dto.BankDataDtos.BankDataUpdateDto;
import com.eside.payment.exception.EntityNotFoundException;
import com.eside.payment.model.BankData;
import com.eside.payment.model.Wallet;
import com.eside.payment.repository.BankDataRepository;
import com.eside.payment.repository.WalletRepository;
import com.eside.payment.service.IBankDataService;
import com.eside.payment.utils.SuccessDto;
import com.eside.payment.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BankDataServiceImpl implements IBankDataService {

    private final WalletRepository walletRepository;
    private final BankDataRepository iBankDataRepository;

    private final ModelMapper modelMapper;
    @Override
    public SuccessDto addBankData(BankDataNewDto bankDataNewDto) {

        Wallet wallet = walletRepository.findById(bankDataNewDto.getWallet_id())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        BankData bankData = BankData.builder()
                .wallet(wallet)
                .bankName(bankDataNewDto.getBankName())
                .rib(bankDataNewDto.getRib())
                .build();
        iBankDataRepository.save(bankData);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto updateBankData(BankDataUpdateDto bankDataUpdateDto) {
        BankData bankData = iBankDataRepository.findById(bankDataUpdateDto.getBankDataId())
                .orElseThrow(() -> new EntityNotFoundException("BankData not found"));

        modelMapper.map(bankDataUpdateDto, bankData);
        iBankDataRepository.save(bankData);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public BankDataDto getBankDataById(Long bankDataId) {
        Optional<BankData> bankData = iBankDataRepository.findById(bankDataId);
        if (bankData.isEmpty()) {
            throw new EntityNotFoundException("BankData not found");
        }
        return BankDataDto.customMapping(bankData.get());
    }

    @Override
    public List<BankDataDto> getAllBankData() {
        return iBankDataRepository.findAll()
                .stream()
                .map(BankDataDto::customMapping)
                .collect(Collectors.toList());
    }


    @Override
    public SuccessDto deleteBankDataById(Long bankDataId) {
        Optional<BankData> bankData = iBankDataRepository.findById(bankDataId);
        if(bankData.isEmpty())
            throw new EntityNotFoundException("BankData not found!");
        iBankDataRepository.delete(bankData.get());
        return SuccessDto
                .builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }
}


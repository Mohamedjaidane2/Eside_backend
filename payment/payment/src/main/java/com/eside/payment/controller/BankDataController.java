package com.eside.payment.controller;

import com.eside.payment.dto.BankDataDtos.BankDataDto;
import com.eside.payment.dto.BankDataDtos.BankDataNewDto;
import com.eside.payment.dto.BankDataDtos.BankDataUpdateDto;
import com.eside.payment.service.IBankDataService;
import com.eside.payment.utils.SuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api("/bank-data")
@RequestMapping("/api/bank-data")
@RestController
@RequiredArgsConstructor
public class BankDataController {
    private final IBankDataService bankDataService;

    @PostMapping("/add")
    public ResponseEntity<SuccessDto> addBankData(@RequestBody BankDataNewDto bankDataNewDto) {
        return ResponseEntity.ok(bankDataService.addBankData(bankDataNewDto));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessDto> updateBankData(@RequestBody BankDataUpdateDto bankDataUpdateDto) {
        return ResponseEntity.ok(bankDataService.updateBankData(bankDataUpdateDto));
    }

    @GetMapping("/{bankDataId}")
    public ResponseEntity<BankDataDto> getBankDataById(@PathVariable Long bankDataId) {
        BankDataDto bankDataDto = bankDataService.getBankDataById(bankDataId);
        return ResponseEntity.ok(bankDataDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BankDataDto>> getAllBankData() {
        List<BankDataDto> bankDataList = bankDataService.getAllBankData();
        return ResponseEntity.ok(bankDataList);
    }

    @DeleteMapping("/delete/{bankDataId}")
    public ResponseEntity<SuccessDto> deleteBankDataById(@PathVariable Long bankDataId) {
        return ResponseEntity.ok(bankDataService.deleteBankDataById(bankDataId));
    }
}

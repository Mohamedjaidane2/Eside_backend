package com.eside.account.controller;

import com.eside.account.dtos.AccountDtos.AccountDto;
import com.eside.account.dtos.AccountDtos.NewAccountDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackUpdateDto;
import com.eside.account.dtos.InformationDtos.InformationDto;
import com.eside.account.dtos.InformationDtos.InformationNewDto;
import com.eside.account.dtos.InformationDtos.InformationUpdateDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.model.Account;
import com.eside.account.service.InformationService;
import com.eside.account.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/information")
@RestController
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @PostMapping("/create")
    //@ApiOperation(value = "Create account")
    public ResponseEntity<SuccessDto> create (@RequestBody InformationNewDto informationNewDto) {
        return ResponseEntity.ok(informationService.addInformation(informationNewDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SuccessDto> update(@RequestBody InformationUpdateDto informationUpdateDto , @PathVariable Long id) {
        return ResponseEntity.ok(informationService.updateInformation(informationUpdateDto,id));
    }

    @GetMapping("/get/accountid/{id}")
    public ResponseEntity<InformationDto> getByAccountId(@PathVariable Long id) {
        InformationDto informationDto = informationService.getInformationByAccountId(id);
        return ResponseEntity.ok(informationDto);
    }

    @PostMapping("/get/informationId")
    public ResponseEntity<InformationDto> getById(@RequestBody Long id) {
        InformationDto informationDto = informationService.getInformationById(id);
        return ResponseEntity.ok(informationDto);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<InformationDto>> getAll() {
        List<InformationDto> informationDtos  = informationService.getAllInformation();
        return ResponseEntity.ok(informationDtos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessDto> deleteFeedBackById(@PathVariable Long id) {
        return ResponseEntity.ok(informationService.deleteInformationById(id));
    }

}

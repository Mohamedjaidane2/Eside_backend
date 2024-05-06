package com.eside.account.service.impl;

import com.eside.account.client.ImageClient;
import com.eside.account.dtos.FeedBackDtos.FeedBackDto;
import com.eside.account.dtos.InformationDtos.InformationDto;
import com.eside.account.dtos.InformationDtos.InformationNewDto;
import com.eside.account.dtos.InformationDtos.InformationUpdateDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.exception.EntityNotFoundException;
import com.eside.account.exception.InvalidOperationException;
import com.eside.account.externalDto.ImageDto;
import com.eside.account.model.Account;
import com.eside.account.model.FeedBack;
import com.eside.account.model.Information;
import com.eside.account.repository.AccountRepository;
import com.eside.account.repository.FeedBackRepository;
import com.eside.account.repository.InformationRepository;
import com.eside.account.service.InformationService;
import com.eside.account.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private ImageClient imageClient;
    @Override
    public SuccessDto addInformation(InformationNewDto informationNewDto) {
        Account account = accountRepository.findById(informationNewDto.getAccountId())
                .orElseThrow(()-> new EntityNotFoundException("account not found !"));

        Information testExistInformation = informationRepository.findByAccount(account);
        if (testExistInformation == null){
            Information information= Information.builder()
                    .creationDate(new Date())
                    .city(informationNewDto.getCity())
                    .postalCode(informationNewDto.getPostalCode())
                    .address(informationNewDto.getAddress())
                    .optionalAddress(informationNewDto.getOptionalAddress())
                    .bio(informationNewDto.getBio())
                    .account(account)
                    .phoneNumber(informationNewDto.getPhoneNumber())
                    .profilePicture(informationNewDto.getProfilePicture())
                    .build();
            informationRepository.save(information);
            account.setInformation(information);
            accountRepository.save(account);
        }else {
            throw new InvalidOperationException("this account already have Information ");
        }


        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto updateInformation(InformationUpdateDto informationUpdateDto, Long infoId) {

        //TODO Return Image from OpenFiegen Path +++++++
        // TODO use try catch and check if profile picture not null
        // TODO the use external api using Open feigen
        // TODO the use external api using Open feigen

        Information information = informationRepository.findById(infoId)
                .orElseThrow(() -> new EntityNotFoundException("Information not found"));
        if(informationUpdateDto.getProfilePicture()!=null){
        ImageDto image = imageClient.getByNameFromAccount(informationUpdateDto.getProfilePicture());
        information.setProfilePicture(image.getPath());

        }

        //modelMapper.map(informationUpdateDto, information);
        information.setAddress(informationUpdateDto.getAddress());
        information.setOptionalAddress(informationUpdateDto.getOptionalAddress());
        information.setPhoneNumber(informationUpdateDto.getPhoneNumber());
        information.setCity(informationUpdateDto.getCity());
        information.setPostalCode(informationUpdateDto.getPostalCode());
        information.setAddress(informationUpdateDto.getAddress());
        information.setBio(informationUpdateDto.getBio());
        information.setUpdateDate(new Date());
        informationRepository.save(information);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public InformationDto getInformationByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new EntityNotFoundException("account not found !"));
        Information information = informationRepository.findByAccount(account);
        return InformationDto.customMapping(information);
    }

    @Override
    public InformationDto getInformationById(Long informationId) {
        Information information = informationRepository.findById(informationId).orElseThrow(() -> new EntityNotFoundException("information not found"));
        return InformationDto.customMapping(information);
    }

    @Override
    public List<InformationDto> getAllInformation() {
        return informationRepository.findAll()
                .stream()
                .map(InformationDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto deleteInformationById(Long informationId) {

        Information information = informationRepository.findById(informationId)
                .orElseThrow(()-> new EntityNotFoundException("information not found !"));;

        informationRepository.delete(information);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }
}

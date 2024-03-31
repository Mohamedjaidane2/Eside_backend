package com.eside.account.service;

import com.eside.account.dtos.InformationDtos.InformationDto;
import com.eside.account.dtos.InformationDtos.InformationNewDto;
import com.eside.account.dtos.InformationDtos.InformationUpdateDto;
import com.eside.account.dtos.SuccessDto;

import java.util.List;

public interface InformationService {
    SuccessDto addInformation(InformationNewDto informationNewDto);

    SuccessDto updateInformation(InformationUpdateDto informationUpdateDto , Long infoId);

    InformationDto getInformationByAccountId(Long AccountId);

    InformationDto getInformationById(Long informationId);

    List<InformationDto> getAllInformation();

    SuccessDto deleteInformationById(Long informationId);
}

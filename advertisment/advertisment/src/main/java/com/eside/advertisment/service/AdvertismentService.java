package com.eside.advertisment.service;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.model.Advertisment;

import java.util.List;
public interface AdvertismentService {
    SuccessDto postAdvertisement(AdvertisementNewDto advertisementNewDto);

    SuccessDto updateAdvertisement(AdvertisementUpdateDtos advertisementUpdateDto, Long advertisementId);

    AdvertisementDto getAdvertisementById(Long advertisementId);

    List<AdvertisementDto> getAdvertisementByAccount(Long accountId);

    List<AdvertisementDto> getAllAdvertisement();
    List<AdvertisementDto> getMyFeed(Long accountId);

    SuccessDto deleteAdvertisement(Long advertisementId );

    SuccessDto changerAdvertismentStatusWhileOrdering(Long OrderId,Long advertismentId);

    List<AdvertisementDto> getAllBySubCategoryName(String CategoryName,Long userAccoundId);

    List<AdvertisementDto> getTop10ByCreationDate(Long userAccoundId);

    List<AdvertisementDto> getALLByCreationDate(Long userAccoundId);
}

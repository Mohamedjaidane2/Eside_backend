package com.eside.advertisment.service;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.SuccessDto;
import java.util.List;
public interface AdvertismentService {
    SuccessDto postAdvertisement(AdvertisementNewDto advertisementNewDto);

    SuccessDto updateAdvertisement(AdvertisementUpdateDtos advertisementUpdateDto, Long advertisementId);

    AdvertisementDto getAdvertisementById(Long advertisementId);

    List<AdvertisementDto> getAdvertisementByAccount(Long accountId);

    List<AdvertisementDto> getAllAdvertisement();

    SuccessDto deleteAdvertisement(Long advertisementId );

    SuccessDto changerAdvertismentStatusWhileOrdering(Long OrderId,Long advertismentId);
}

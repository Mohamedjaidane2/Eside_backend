package com.eside.advertisment.service;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.FilterDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.model.Advertisment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AdvertismentService {
    SuccessDto postAdvertisement(AdvertisementNewDto advertisementNewDto);

    SuccessDto updateAdvertisement(AdvertisementUpdateDtos advertisementUpdateDto, Long advertisementId);

    AdvertisementDto getAdvertisementById(Long advertisementId);

    List<AdvertisementDto> getAdvertisementByAccount(Long accountId);

    List<AdvertisementDto> getAllAdvertisement();
    Map<String, Object> getMyFeed(Long accountId,int page , int size);

    Map<String, Object> findAdvertisementsByFilter(List<FilterDto> filterDTOList,int page , int size);

    SuccessDto deleteAdvertisement(Long advertisementId );

    SuccessDto changerAdvertismentStatusWhileOrdering(Long OrderId,Long advertismentId);

    Map<String, Object> getAllBySubCategoryName(String CategoryName, Long userAccoundId ,int page , int size);

    List<AdvertisementDto> getTop10ByCreationDate(Long userAccoundId);

    List<AdvertisementDto> getALLByCreationDate(Long userAccoundId);
}

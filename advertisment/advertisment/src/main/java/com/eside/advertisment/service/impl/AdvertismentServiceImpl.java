package com.eside.advertisment.service.impl;

import com.eside.advertisment.client.AccountClient;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.enums.AdvertisementSoldStatusEnum;
import com.eside.advertisment.enums.AdvertisementStatusEnum;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.externalData.Account;
import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Product;
import com.eside.advertisment.repository.AdvertismentRepository;
import com.eside.advertisment.repository.ProductRepository;
import com.eside.advertisment.service.AdvertismentService;
import com.eside.advertisment.utils.SuccessMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertismentServiceImpl implements AdvertismentService {
    private final AdvertismentRepository advertismentRepository;
    private final ProductRepository productRepository;
    private final AccountClient accountClient;
    private final ModelMapper modelMapper;


    @Override
    public SuccessDto postAdvertisement(AdvertisementNewDto advertisementNewDto) {
        Optional<Product> optionalProduct = productRepository.findById(advertisementNewDto.getProductId());

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }

        Product product = optionalProduct.get();

        Account account = accountClient.getAccountByIdFromAccount(advertisementNewDto.getAccountId());

        Advertisment advertisement = Advertisment.builder()
                .userAccountId(account.getId())
                .product(product)
                .title(advertisementNewDto.getTitle())
                .description(advertisementNewDto.getDescription())
                .advertisementStatusEnum(AdvertisementStatusEnum.NO_VALUE)
                .advertisementSoldStatusEnum(AdvertisementSoldStatusEnum.AVAILABLE)
                .oldPrice(advertisementNewDto.getPrice())
                .price(advertisementNewDto.getPrice())
                .creationDate(new Date())
                .build();
        System.out.println("advertisement" + advertisement);
        advertismentRepository.save(advertisement);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }


    @Override
    public SuccessDto updateAdvertisement(AdvertisementUpdateDtos advertisementUpdateDto, Long advertisementId) {
        Advertisment existingAdvertisement = advertismentRepository.findById(advertisementId)
                .orElseThrow(()-> {
                           throw  new EntityNotFoundException("Advertisement not found");
                        }
                );
        existingAdvertisement.setPrice(advertisementUpdateDto.getPrice());
        existingAdvertisement.setOldPrice(advertisementUpdateDto.getPrice());
        existingAdvertisement.setTitle(advertisementUpdateDto.getTitle());
        existingAdvertisement.setDescription(advertisementUpdateDto.getDescription());
        existingAdvertisement.setUpdateDate(new Date());

        advertismentRepository.save(existingAdvertisement);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public AdvertisementDto getAdvertisementById(Long advertisementId) {
        Advertisment existingAdvertisement = advertismentRepository.findById(advertisementId)
                .orElseThrow(()-> {
                            throw  new EntityNotFoundException("Advertisement not found");
                        }
                );
        return AdvertisementDto.customMapping(existingAdvertisement);
    }

    @Override
    public List<AdvertisementDto> getAdvertisementByAccount(Long accountId) {
        List<Advertisment> Advertisements = advertismentRepository.findByUserAccountId(accountId);
        return AdvertisementDto.customListMapping(Advertisements);
    }

    @Override
    public List<AdvertisementDto> getAllAdvertisement() {
        return advertismentRepository.findAll()
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getMyFeed(Long accountId) {
        return advertismentRepository.findAllByUserAccountIdNot(accountId)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto deleteAdvertisement(Long advertisementId) {
        Advertisment existingAdvertisement = advertismentRepository.findById(advertisementId)
                .orElseThrow(()-> {
                            throw  new EntityNotFoundException("Advertisement not found");
                        }
                );
        advertismentRepository.delete(existingAdvertisement);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }

    @Override
    public SuccessDto changerAdvertismentStatusWhileOrdering(Long OrderId,Long advertismentId) {
        Advertisment existingAdvertisement = advertismentRepository.findById(advertismentId)
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found")
                );
                existingAdvertisement.setOrderId(OrderId);
                existingAdvertisement.setAdvertisementSoldStatusEnum(AdvertisementSoldStatusEnum.IN_PROGRESS);
                advertismentRepository.save(existingAdvertisement);
        return SuccessDto.builder()
                .message(SuccessMessage.STATUS_CHANGED)
                .build();
    }

    @Override
    public List<AdvertisementDto> getAllBySubCategoryName(String CategoryName,Long userAccoundId) {
        return advertismentRepository.findAllByProduct_SubCategory_NameAndUserAccountIdNot(CategoryName,userAccoundId)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getTop10ByCreationDate(Long userAccoundId) {
        return advertismentRepository.findTop10ByUserAccountIdNotOrderByCreationDateDesc(userAccoundId)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getALLByCreationDate(Long userAccoundId) {
        return advertismentRepository.findByUserAccountIdNotOrderByCreationDateDesc(userAccoundId)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }
}

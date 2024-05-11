package com.eside.advertisment.service.impl;

import com.eside.advertisment.client.AccountClient;
import com.eside.advertisment.client.FavoritesClient;
import com.eside.advertisment.client.OrderClient;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.FilterDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.enums.AdvertisementSoldStatusEnum;
import com.eside.advertisment.enums.AdvertisementStatusEnum;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.exception.InvalidOperationException;
import com.eside.advertisment.externalData.Account;
import com.eside.advertisment.externalData.FavortieDto;
import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Product;
import com.eside.advertisment.repository.AdvertismentRepository;
import com.eside.advertisment.repository.ProductRepository;
import com.eside.advertisment.service.AdvertismentService;
import com.eside.advertisment.service.ProductService;
import com.eside.advertisment.specification.AdvertismentSpecification;
import com.eside.advertisment.utils.SuccessMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertismentServiceImpl implements AdvertismentService {
    private final AdvertismentRepository advertismentRepository;
    private final ProductRepository productRepository;
    private final ProductServiceImpl productService;
    private final AccountClient accountClient;
    private final FavoritesClient favoritesClient;
    private final OrderClient orderClient;
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
                .ownerAccountName(account.getAccountName())
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
    public List<AdvertisementDto> getGallery(Long accountId) {
        List<Advertisment> Advertisements = advertismentRepository.findByUserAccountIdAndAdvertisementStatusEnum(accountId,AdvertisementStatusEnum.ACCEPTED);
        return AdvertisementDto.customListMapping(Advertisements);
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
    public Map<String, Object> getMyFeed(Long accountId,int page , int size) {
        try {
            List<AdvertisementDto> advertisment = new ArrayList<AdvertisementDto>();
            Pageable paging = PageRequest.of(page,size);
            Page<Advertisment> pageAds;
            if (accountId == null) {
                //pageAds = advertismentRepository.findAll(paging);
                return null;
            }else {
                pageAds = advertismentRepository.findAllByUserAccountIdNotAndAdvertisementStatusEnum(accountId, paging , AdvertisementStatusEnum.ACCEPTED);
                advertisment = pageAds.getContent().stream()
                        .map(AdvertisementDto::customMapping)
                        .collect(Collectors.toList());
                Map<String,Object> response = new HashMap<>();
                response.put("advertisments",advertisment);
                response.put("currentPage",pageAds.getNumber());
                response.put("totalItems",pageAds.getTotalElements());
                response.put("totalPages",pageAds.getTotalPages());
                return response;
            }
        }catch (Exception e ){
            throw new InvalidOperationException("Invalid opération");

        }
    }
    @Override
    public Map<String, Object> findAdvertisementsByFilter(List<FilterDto> filterDtoList, int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Advertisment> pageAds = advertismentRepository.findAll(AdvertismentSpecification.createSpecification(filterDtoList), paging);

            List<AdvertisementDto> advertisment = pageAds.getContent().stream()
                    .map(AdvertisementDto::customMapping)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("advertisments", advertisment);
            response.put("currentPage", pageAds.getNumber());
            response.put("totalItems", pageAds.getTotalElements());
            response.put("totalPages", pageAds.getTotalPages());
            return response;
        } catch (Exception e) {
            throw new InvalidOperationException("Invalid operation");
        }
    }

    @Override
    public SuccessDto checkAdsAndChangeStatus(Long id,AdvertisementStatusEnum advertisementStatusEnum) {
        Advertisment existingAdvertisement = advertismentRepository.findById(id)
                .orElseThrow(()-> {
                            throw  new EntityNotFoundException("Advertisement not found");
                        }
                );
        existingAdvertisement.setAdvertisementStatusEnum(advertisementStatusEnum);
        advertismentRepository.save(existingAdvertisement);
        return SuccessDto.builder()
                .message(SuccessMessage.STATUS_CHANGED)
                .build();
    }


    @Override
    public SuccessDto deleteAdvertisement(Long advertisementId, Long accountId) {
        // Retrieve the advertisement by ID or throw an exception if not found
        Advertisment existingAdvertisement = advertismentRepository.findById(advertisementId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));

        // Check if the account ID matches the advertisement's owner, if not, throw an exception
        validateOwner(existingAdvertisement, accountId);

        // Check if the advertisement has an active order, if yes, throw an exception
        validateOrder(existingAdvertisement);

        // Delete the associated order
        SuccessDto orderDeletionResult = deleteAssociatedOrder(existingAdvertisement.getOrderId());

        // Delete the associated product
        deleteAssociatedProduct(existingAdvertisement.getProduct().getId());
        // Optional: Delete the advertisement entity from the repository
        advertismentRepository.delete(existingAdvertisement);

        // Return success message
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }
    private void deleteAssociatedProduct(Long productId) {
        // Assuming productService.deleteProductById deletes the product by its ID
        productService.deleteProductById(productId);
    }
    // Method to validate if the provided account ID matches the advertisement's owner
    private void validateOwner(Advertisment advertisement, Long accountId) {
        if (!Objects.equals(advertisement.getUserAccountId(), accountId)) {
            throw new InvalidOperationException("You cannot delete this advertisement");
        }
    }

    // Method to validate if the advertisement has an active order
    private void validateOrder(Advertisment advertisement) {
        if (advertisement.getOrderId() != null) {
            throw new InvalidOperationException("You cannot delete an advertisement with an active order");
        }
    }

    // Method to delete the associated order
    private SuccessDto deleteAssociatedOrder(Long orderId) {
        if (orderId != null) {
            // Assuming orderClient.deleteOrder returns a SuccessDto
            return orderClient.deleteOrder(orderId);
        }
        // If there is no associated order, return a success message
        return SuccessDto.builder()
                .message("No associated order found for deletion")
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
    public SuccessDto changerAdvertismentStatusWhilePayment(Long OrderId,Long advertismentId) {
        Advertisment existingAdvertisement = advertismentRepository.findById(advertismentId)
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found")
                );
                existingAdvertisement.setAdvertisementSoldStatusEnum(AdvertisementSoldStatusEnum.SOLD);
                advertismentRepository.save(existingAdvertisement);
        return SuccessDto.builder()
                .message(SuccessMessage.STATUS_CHANGED)
                .build();
    }

    @Override
    public Map<String, Object> getAllBySubCategoryName(String CategoryName, Long userAccoundId, int page , int size) {
        try {
        List<AdvertisementDto> advertisment = new ArrayList<AdvertisementDto>();
        Pageable paging = PageRequest.of(page,size);
        Page<Advertisment> pageAds;
        if (CategoryName == null) {
            //pageAds = advertismentRepository.findAll(paging);
            return null;
        }else {
            pageAds = advertismentRepository.findAllByUserAccountIdNotAndAdvertisementStatusEnum(userAccoundId, paging, AdvertisementStatusEnum.ACCEPTED);
            advertisment = pageAds.getContent().stream()
                    .map(AdvertisementDto::customMapping)
                    .collect(Collectors.toList());
            Map<String,Object> response = new HashMap<>();
            response.put("advertisments",advertisment);
            response.put("currentPage",pageAds.getNumber());
            response.put("totalItems",pageAds.getTotalElements());
            response.put("totalPages",pageAds.getTotalPages());
            return response;
        }
        }catch (Exception e ){
            throw new InvalidOperationException("Invalid opération");

        }
        //return advertismentRepository.findAllByProduct_SubCategory_NameAndUserAccountIdNot(CategoryName,userAccoundId)
        //        .stream()
        //        .map(AdvertisementDto::customMapping)
        //        .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getTop10BySubCategoryNameNoAuth(String CategoryName) {
        return advertismentRepository.findTop10ByProduct_SubCategory_NameAndAdvertisementStatusEnumOrderByCreationDateDesc(CategoryName,AdvertisementStatusEnum.ACCEPTED)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());

    }

    @Override
    public List<AdvertisementDto> getTop10BySubCategoryNamewithAuth(String CategoryName, Long userAccoundId) {
        return advertismentRepository.findTop10ByProduct_SubCategory_NameAndUserAccountIdNotAndAdvertisementStatusEnumOrderByCreationDateDesc(CategoryName,userAccoundId,AdvertisementStatusEnum.ACCEPTED)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getTop10ByCreationDate(Long userAccoundId) {
        return advertismentRepository.findTop10ByUserAccountIdNotAndAdvertisementStatusEnumOrderByCreationDateDesc(userAccoundId,AdvertisementStatusEnum.ACCEPTED)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getTop10ByCreationDateNoAuth() {
        return advertismentRepository.findTop10ByAdvertisementStatusEnumOrderByCreationDateDesc(AdvertisementStatusEnum.ACCEPTED)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getALLByCreationDate(Long userAccoundId) {
        return advertismentRepository.findByUserAccountIdNotAndAdvertisementStatusEnumOrderByCreationDateDesc(userAccoundId,AdvertisementStatusEnum.ACCEPTED)
                .stream()
                .map(AdvertisementDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getFavoritesByAccount(Long userAccoundId) {
        List<AdvertisementDto> advertisementDtoList = new ArrayList<>();
        List<FavortieDto> favortieDtoList = favoritesClient.getfavorites(userAccoundId);
        if (!favortieDtoList.isEmpty()){
        for(FavortieDto fav : favortieDtoList){
        Advertisment advertisment = advertismentRepository.findById(fav.getAdvertismentId())
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found"));
        advertisementDtoList.add(AdvertisementDto.customMapping(advertisment));
        }
         return advertisementDtoList;
        }
        return advertisementDtoList;
    }

    @Override
    public boolean isAvailable(Long Id) {
        Advertisment advertisement = advertismentRepository.findById(Id)
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found")
                );
        if (advertisement.getAdvertisementSoldStatusEnum()==AdvertisementSoldStatusEnum.AVAILABLE){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public SuccessDto deleteOrder(Long advertismentId) {
        Advertisment advertisement = advertismentRepository.findById(advertismentId)
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found")
                );
        advertisement.setOrderId(null);
        advertisement.setAdvertisementSoldStatusEnum(AdvertisementSoldStatusEnum.AVAILABLE);
        return SuccessDto.builder()
                .message("Updated !")
                .build();
    }
}

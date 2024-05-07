package com.eside.advertisment.controller;

import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.FilterDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.enums.AdvertisementStatusEnum;
import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.service.AdvertismentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@Api("/advertisement")
@RequestMapping("/api/advertisement")
@RestController
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertismentService advertisementService;

    @PostMapping("/post")
    //@ApiOperation(value = "Post advertisement")
    public ResponseEntity<SuccessDto> postAdvertisement(@RequestBody AdvertisementNewDto advertisementNewDto) {
        return ResponseEntity.ok(advertisementService.postAdvertisement(advertisementNewDto));
    }

    @PutMapping("/update")
   // @ApiOperation(value = "Update advertisement")
    public ResponseEntity<SuccessDto> updateAdvertisement(@RequestBody AdvertisementUpdateDtos advertisementUpdateDto,Long advertisementId) {
        return ResponseEntity.ok(advertisementService.updateAdvertisement(advertisementUpdateDto,advertisementId));
    }
    @PutMapping("/change-status/{Id}/{advertisementStatusEnum}")
   // @ApiOperation(value = "Update advertisement")
    public ResponseEntity<SuccessDto> aprouveOrDecline(@PathVariable Long Id, @PathVariable AdvertisementStatusEnum advertisementStatusEnum) {
        return ResponseEntity.ok(advertisementService.checkAdsAndChangeStatus(Id,advertisementStatusEnum));
    }
    @PutMapping("/updateWhileOrder/{OrderId}/{advertisementId}")
   // @ApiOperation(value = "Update advertisement")
    public ResponseEntity<SuccessDto> changerAdvertismentStatusWhileOrdering(@PathVariable Long OrderId, @PathVariable Long advertisementId) {
        return ResponseEntity.ok(advertisementService.changerAdvertismentStatusWhileOrdering(OrderId,advertisementId));
    }
    @GetMapping("/{advertisementId}")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<AdvertisementDto> getAdvertisementById(@PathVariable Long advertisementId) {
        AdvertisementDto advertisementDto = advertisementService.getAdvertisementById(advertisementId);
        return ResponseEntity.ok(advertisementDto);
    }

    @GetMapping("/all/by/{categoryName}/{userAccoundId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<Map<String, Object>> getAllBySubCategoryName(
            @PathVariable String categoryName,
            @PathVariable Long userAccoundId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size

    ) {
        Map<String, Object> advertisements = advertisementService.getAllBySubCategoryName(categoryName,userAccoundId,page,size);
        return ResponseEntity.ok(advertisements);
    }
    @GetMapping("/all/top/10/subcategory/{accountId}/{subcategoryName}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getTop10BySubCategoryWithAuth(@PathVariable Long accountId,@PathVariable String subcategoryName) {
        List<AdvertisementDto> advertisements = advertisementService.getTop10BySubCategoryNamewithAuth(subcategoryName,accountId);
        return ResponseEntity.ok(advertisements);
    }
    @GetMapping("/all/top/10/subcategory/no-auth/{subcategoryName}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getTop10BySubCategoryNoAuth(@PathVariable String subcategoryName) {
        List<AdvertisementDto> advertisements = advertisementService.getTop10BySubCategoryNameNoAuth(subcategoryName);
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/all/top/10/{accountId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getTop10ByCreationDate(@PathVariable Long accountId) {
        List<AdvertisementDto> advertisements = advertisementService.getTop10ByCreationDate(accountId);
        return ResponseEntity.ok(advertisements);
    }
    @GetMapping("/all/top/10/no-auth")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getTop10ByCreationDateNoAuth() {
        List<AdvertisementDto> advertisements = advertisementService.getTop10ByCreationDateNoAuth();
        return ResponseEntity.ok(advertisements);
    }
    @GetMapping("/all/recent/{accountId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<Map<String, Object>> getALLByCreationDate(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
            ) {
        Map<String, Object> advertisements = advertisementService.getMyFeed(accountId,page,size);
        return ResponseEntity.ok(advertisements);
    }
    @GetMapping("/account/{accountId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getAdvertisementByAccount(@PathVariable Long accountId) {
        List<AdvertisementDto> advertisements = advertisementService.getAdvertisementByAccount(accountId);
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/gellery/{accountId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getGallery(@PathVariable Long accountId) {
        List<AdvertisementDto> advertisements = advertisementService.getGallery(accountId);
        return ResponseEntity.ok(advertisements);
    }
    @GetMapping("/all")
   // @ApiOperation(value = "Get all advertisements")
    public ResponseEntity<List<AdvertisementDto>> getAllAdvertisements() {
        List<AdvertisementDto> advertisements = advertisementService.getAllAdvertisement();
        return ResponseEntity.ok(advertisements);
    }
    @PostMapping("/all/byFilter")
    public ResponseEntity<Map<String, Object>> getAllAdvertisementsByFilter(
            @RequestBody List<FilterDto> filterDTOList,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> advertisements = advertisementService.findAdvertisementsByFilter(filterDTOList, page, size);
        return ResponseEntity.ok(advertisements);
    }

    @DeleteMapping("/delete/{advertisementId}/{accountId}")
    //@ApiOperation(value = "Delete advertisement by ID")
    public ResponseEntity<SuccessDto> deleteAdvertisement(@PathVariable Long advertisementId,@PathVariable Long accountId) {
        return ResponseEntity.ok(advertisementService.deleteAdvertisement(advertisementId,accountId));
    }


    @GetMapping("/favorites/{accountId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getfavorites(@PathVariable Long accountId) {
        List<AdvertisementDto> advertisements = advertisementService.getFavoritesByAccount(accountId);
        return ResponseEntity.ok(advertisements);
    }

}

package com.eside.advertisment.controller;

import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementUpdateDtos;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.service.AdvertismentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/account/{accountId}")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<AdvertisementDto>> getAdvertisementByAccount(@PathVariable Long accountId) {
        List<AdvertisementDto> advertisements = advertisementService.getAdvertisementByAccount(accountId);
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/all")
   // @ApiOperation(value = "Get all advertisements")
    public ResponseEntity<List<AdvertisementDto>> getAllAdvertisements() {
        List<AdvertisementDto> advertisements = advertisementService.getAllAdvertisement();
        return ResponseEntity.ok(advertisements);
    }

    @DeleteMapping("/delete/{advertisementId}")
    //@ApiOperation(value = "Delete advertisement by ID")
    public ResponseEntity<SuccessDto> deleteAdvertisement(@PathVariable Long advertisementId) {
        return ResponseEntity.ok(advertisementService.deleteAdvertisement(advertisementId));
    }


}

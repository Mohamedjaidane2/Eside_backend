package com.eside.Order.controller;

import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestNewDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestUpdateCounterDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestUpdateDto;
import com.eside.Order.dtos.SuccessDto;
import com.eside.Order.service.impl.DiscountRequestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/discount-request")
@RestController
@RequiredArgsConstructor
public class DiscountRequestController {
    private final DiscountRequestServiceImpl discountRequestService;

    @PostMapping("/send")
    //@ApiOperation(value = "Send a discount request")
    public ResponseEntity<SuccessDto> sendDiscountRequest(@RequestBody DiscountRequestNewDto discountRequestNewDto) {
        return ResponseEntity.ok(discountRequestService.sendDiscount(discountRequestNewDto));
    }

    @PutMapping("/counter/{discountRequestId}")
    //@ApiOperation(value = "Counter a discount request")
    public ResponseEntity<SuccessDto> counterDiscountRequest(@PathVariable Long discountRequestId, @RequestBody DiscountRequestUpdateCounterDto discountRequestUpdateCounterDto) {
        return ResponseEntity.ok(discountRequestService.counterDiscount(discountRequestId, discountRequestUpdateCounterDto));
    }

    @PutMapping("/update/{discountRequestId}")
    //@ApiOperation(value = "Update a discount request")
    public ResponseEntity<SuccessDto> updateDiscountRequest(@PathVariable Long discountRequestId, @RequestBody DiscountRequestUpdateDto discountRequestUpdateDto) {
        return ResponseEntity.ok(discountRequestService.updateDiscount(discountRequestId, discountRequestUpdateDto));
    }

    @GetMapping("/{discountId}")
    //@ApiOperation(value = "Get discount request by ID")
    public ResponseEntity<DiscountRequestDto> getDiscountRequestById(@PathVariable Long discountId) {
        DiscountRequestDto discountRequestDto = discountRequestService.getDiscountById(discountId);
        return ResponseEntity.ok(discountRequestDto);
    }

    @GetMapping("/all")
    //@ApiOperation(value = "Get all discount requests")
    public ResponseEntity<List<DiscountRequestDto>> getAllDiscountRequests() {
        List<DiscountRequestDto> discountRequests = discountRequestService.getAllDiscount();
        return ResponseEntity.ok(discountRequests);
    }

    @DeleteMapping("/delete/{discountId}")
    //@ApiOperation(value = "Delete a discount request by ID")
    public ResponseEntity<SuccessDto> deleteDiscountRequestById(@PathVariable Long discountId) {
        return ResponseEntity.ok(discountRequestService.deleteDiscountRequestById(discountId));
    }


    @GetMapping("/advertisementOowner/{advertisementOwnerId}")
    //@ApiOperation(value = "Get discount requests by advertisement owner ID")
    public ResponseEntity<List<DiscountRequestDto>> getDiscountsByAdvertisementOwnerId(@PathVariable Long advertisementOwnerId) {
        List<DiscountRequestDto> discountRequests = discountRequestService.getDiscountByAdvertisementOwnerId(advertisementOwnerId);
        return ResponseEntity.ok(discountRequests);
    }
    @PutMapping("/accept/{discountId}")
    //@ApiOperation(value = "Accept a discount request")
    public ResponseEntity<SuccessDto> acceptDiscount(@PathVariable Long discountId) {
        return ResponseEntity.ok(discountRequestService.acceptDiscount(discountId));
    }

    @PutMapping("/decline/{discountId}")
    //@ApiOperation(value = "Decline a discount request")
    public ResponseEntity<SuccessDto> declineDiscount(@PathVariable Long discountId) {
        return ResponseEntity.ok(discountRequestService.declineDiscount(discountId));
    }
}

package com.eside.account.controller;

import com.eside.account.dtos.FeedBackDtos.FeedBackDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackNewDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackUpdateDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.service.impl.FeedBackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/feedBack")
@RestController
@RequiredArgsConstructor
public class FeedBackController {
    private final FeedBackServiceImpl feedBackService;

    @PostMapping("/create")
    public ResponseEntity<SuccessDto> create (@RequestBody FeedBackNewDto feedBackNewDto) {
        return ResponseEntity.ok(feedBackService.addFeedBack(feedBackNewDto));
    }

    @PutMapping("/update/{id}/{senderAccountName}")
    public ResponseEntity<SuccessDto> update(@PathVariable Long id , @RequestBody FeedBackUpdateDto feedBackUpdateDto , @PathVariable String senderAccountName) {
        return ResponseEntity.ok(feedBackService.updateFeedBack(feedBackUpdateDto,id,senderAccountName));
    }

    @PostMapping("/get/id")
    public ResponseEntity<FeedBackDto> getById(@RequestBody Long id) {
        FeedBackDto feedBackDto = feedBackService.getFeedBackById(id);
        return ResponseEntity.ok(feedBackDto);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<FeedBackDto>> getAll() {
        List<FeedBackDto> allFeedBacks = feedBackService.getAllFeedBack();
        return ResponseEntity.ok(allFeedBacks);
    }

    @PostMapping("/get/reciver")
    public ResponseEntity<List<FeedBackDto>> getAllRecivedFeedBackByAccount(@RequestBody Long id) {
        List<FeedBackDto> feedBackDto = feedBackService.getAllRecivedFeedBackByAccount(id);
        return ResponseEntity.ok(feedBackDto);
    }
    @PostMapping("/get/sender")
    public ResponseEntity<List<FeedBackDto>> getAllSendedFeedBackByAccount(@RequestBody Long id) {
        List<FeedBackDto> feedBackDto = feedBackService.getAllSendedFeedBackByAccount(id);
        return ResponseEntity.ok(feedBackDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessDto> deleteFeedBackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedBackService.deleteFeedBackById(id));
    }
}

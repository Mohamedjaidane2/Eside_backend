package com.eside.account.service.impl;

import com.eside.account.dtos.FeedBackDtos.FeedBackDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackNewDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackUpdateDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.exception.EntityNotFoundException;
import com.eside.account.exception.InvalidOperationException;
import com.eside.account.model.Account;
import com.eside.account.model.FeedBack;
import com.eside.account.repository.AccountRepository;
import com.eside.account.repository.FeedBackRepository;
import com.eside.account.service.FeedBackService;
import com.eside.account.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    @Override
    public SuccessDto addFeedBack(FeedBackNewDto feedBackNewDto) {

        Account optionalReciver = accountRepository.findByAccountName(feedBackNewDto.getReciverAccountName())
                        .orElseThrow(()-> new EntityNotFoundException("reciver account not found !"));

        Account optionalSender = accountRepository.findByAccountName(feedBackNewDto.getSenderAccountName())
                        .orElseThrow(()-> new EntityNotFoundException("sender account not found !"));
        FeedBack feedBack = FeedBack.builder()
                .description(feedBackNewDto.getDescription())
                .Stars(feedBackNewDto.getStars())
                .creationDate(new Date())
                .receiverAccount(optionalReciver)
                .senderAccount(optionalSender)
                .build();
        feedBackRepository.save(feedBack);
        return SuccessDto.builder().message(SuccessMessage.SUCCESSFULLY_CREATED).build();
    }

    @Override
    public SuccessDto updateFeedBack(FeedBackUpdateDto feedBackUpdateDto , Long feedBackId ,String senderAccountName) {
        FeedBack feedBack = feedBackRepository.findById(feedBackId).orElseThrow(() -> new EntityNotFoundException("feedBack not found"));
        Account optionalSender = accountRepository.findByAccountName(senderAccountName)
                .orElseThrow(()-> new EntityNotFoundException("sender account not found !"));

        if (Objects.equals(optionalSender.getAccountName(), feedBack.getSenderAccount().getAccountName())){

            feedBack.setStars(feedBackUpdateDto.getStars());
            feedBack.setDescription(feedBackUpdateDto.getDescription());

            feedBackRepository.save(feedBack);
            return SuccessDto.builder()
                    .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                    .build();
        }else {
            throw new InvalidOperationException("you are the wrong person ! ");
        }



    }

    @Override
    public FeedBackDto getFeedBackById(Long feedBackId) {
        FeedBack feedBack = feedBackRepository.findById(feedBackId).orElseThrow(() -> new EntityNotFoundException("feedBack not found"));
        return FeedBackDto.customMapping(feedBack);

    }


    @Override
    public List<FeedBackDto> getAllFeedBack() {
        return feedBackRepository.findAll()
                .stream()
                .map(FeedBackDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedBackDto> getAllRecivedFeedBackByAccount(Long accountId) {
        Account optionalSender = accountRepository.findById(accountId)
                .orElseThrow(()-> new EntityNotFoundException("reciver account not found !"));
        List<FeedBack> feedBacks = feedBackRepository.findByReceiverAccount(optionalSender);
        return feedBacks.stream().map(FeedBackDto::customMapping).collect(Collectors.toList());
    }

    @Override
    public List<FeedBackDto> getAllSendedFeedBackByAccount(Long accountId) {
        Account optionalSender = accountRepository.findById(accountId)
                .orElseThrow(()-> new EntityNotFoundException("sender account not found !"));
        List<FeedBack> feedBacks = feedBackRepository.findBySenderAccount(optionalSender);
        return feedBacks.stream().map(FeedBackDto::customMapping).collect(Collectors.toList());
    }

    @Override
    public SuccessDto deleteFeedBackById(Long feedBackId) {
        FeedBack feedBack = feedBackRepository.findById(feedBackId)
                .orElseThrow(()-> new EntityNotFoundException("feedback not found !"));;

        feedBackRepository.delete(feedBack);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }
}

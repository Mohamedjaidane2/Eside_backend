package com.eside.account.service;

import com.eside.account.dtos.AccountDtos.AccountDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackNewDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackUpdateDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.model.Account;

import java.util.List;

public interface FeedBackService {
    SuccessDto addFeedBack(FeedBackNewDto feedBackNewDto);
    SuccessDto updateFeedBack(FeedBackUpdateDto feedBackUpdateDto , Long feedBackId ,String senderAccountName );
    FeedBackDto getFeedBackById(Long feedBackId);
    List<FeedBackDto> getAllFeedBack();
    List<FeedBackDto> getAllRecivedFeedBackByAccount(Long accountId);
    List<FeedBackDto> getAllSendedFeedBackByAccount(Long accountId);
    SuccessDto deleteFeedBackById(Long feedBackId);
}

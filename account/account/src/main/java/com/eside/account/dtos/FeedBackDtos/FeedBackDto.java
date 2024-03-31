package com.eside.account.dtos.FeedBackDtos;

import com.eside.account.model.FeedBack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FeedBackDto {

    private Long id;

    private String description;

    private int Stars;

    private Date creationDate;

    private String senderAccountName;

    private String reciverAccountName;

    public static FeedBackDto customMapping (FeedBack feedBack){
        return FeedBackDto.builder()
                .id(feedBack.getId())
                .description(feedBack.getDescription())
                .Stars(feedBack.getStars())
                .creationDate(feedBack.getCreationDate())
                .reciverAccountName(feedBack.getReceiverAccount().getAccountName())
                .senderAccountName(feedBack.getSenderAccount().getAccountName())
                .build();
    }

    public static List<FeedBackDto> customListMapping (List<FeedBack> feedBacks){
        if(feedBacks==null) return null;
        ArrayList<FeedBackDto> feedBackDtoArrayList = new ArrayList<>();
        for (FeedBack feedBack : feedBacks){
            FeedBackDto feedBackDto = customMapping(feedBack);
            feedBackDtoArrayList.add(feedBackDto);
        }
        return feedBackDtoArrayList;
    }
}

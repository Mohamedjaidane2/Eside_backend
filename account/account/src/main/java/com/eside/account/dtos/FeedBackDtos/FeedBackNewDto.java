package com.eside.account.dtos.FeedBackDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FeedBackNewDto {

    private String description;

    private double stars;

    private String senderAccountName;

    private String reciverAccountName;
}

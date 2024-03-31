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
public class FeedBackUpdateDto {

    private String description;

    private int Stars;

}

package com.eside.account.dtos.FavoritesDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NewFavoriteDto {
    private Long advertismentId ;
    private Long accountId ;
}

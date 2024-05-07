package com.eside.account.service;

import com.eside.account.dtos.AccountDtos.AccountDto;
import com.eside.account.dtos.AccountDtos.NewAccountDto;
import com.eside.account.dtos.FavoritesDtos.FavortieDto;
import com.eside.account.dtos.FavoritesDtos.NewFavoriteDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.model.Account;
import com.eside.account.model.Favorite;

import java.util.List;

public interface FavoriteServices {
    SuccessDto addToFavorite(NewFavoriteDto newFavoriteDto);
    SuccessDto deleteFromFavorite(NewFavoriteDto newFavoriteDto);
    Boolean isSelected(NewFavoriteDto newFavoriteDto);
    FavortieDto getFavoriteById(Long id);
    List<FavortieDto> getAllByAccountId (Long accountId);
}

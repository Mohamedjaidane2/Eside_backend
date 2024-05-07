package com.eside.account.service.impl;

import com.eside.account.client.AdvertismentClient;
import com.eside.account.dtos.FavoritesDtos.FavortieDto;
import com.eside.account.dtos.FavoritesDtos.NewFavoriteDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.exception.EntityNotFoundException;
import com.eside.account.exception.InvalidOperationException;
import com.eside.account.externalDto.Advertisment;
import com.eside.account.model.Account;
import com.eside.account.model.Favorite;
import com.eside.account.model.FeedBack;
import com.eside.account.repository.AccountRepository;
import com.eside.account.repository.FavoriteRepository;
import com.eside.account.service.FavoriteServices;
import com.eside.account.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteServices {
    private final FavoriteRepository favoriteRepository;
    private final AdvertismentClient advertismentClient;
    private final AccountRepository accountRepository;
    @Override
    public SuccessDto addToFavorite(NewFavoriteDto newFavoriteDto) {
       Account account = accountRepository.findById(newFavoriteDto.getAccountId())
                .orElseThrow(()-> new EntityNotFoundException("account not found !"));
        Advertisment advertisment = new Advertisment();
        try {
            advertisment= advertismentClient.getAdvertismentByIdFromAccount(newFavoriteDto.getAdvertismentId());
            //System.out.println("our advertisment " + advertisment);
        }catch (Exception e){
            throw e ;
        }
        Optional<Favorite> favoritetest = favoriteRepository.findByAccountIdAndAdvertismentId(newFavoriteDto.getAccountId(), newFavoriteDto.getAdvertismentId());
        if (favoritetest.isPresent()){
           favoriteRepository.delete(favoritetest.get());
        }else{
            Favorite favorite = Favorite.builder()
                    .account(account)
                    .advertismentId(advertisment.getId())
                    .creationDate(new Date())
                    .build();

            favoriteRepository.save(favorite);
        }

        return SuccessDto.builder().message(SuccessMessage.SUCCESSFULLY_ADDED).build();
    }

    @Override
    public SuccessDto deleteFromFavorite(NewFavoriteDto newFavoriteDto) {
        Favorite favorite = favoriteRepository.findByAccountIdAndAdvertismentId(newFavoriteDto.getAccountId(), newFavoriteDto.getAdvertismentId())
                .orElseThrow(()-> new EntityNotFoundException("favorite not found !"));
        favoriteRepository.delete(favorite);
        return SuccessDto.builder().message(SuccessMessage.SUCCESSFULLY_REMOVED).build();
    }

    @Override
    public Boolean isSelected(NewFavoriteDto newFavoriteDto) {
        Optional<Favorite> favorite = favoriteRepository.findByAccountIdAndAdvertismentId(newFavoriteDto.getAccountId(), newFavoriteDto.getAdvertismentId());
        if(favorite.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public FavortieDto getFavoriteById(Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("favorite not found!"));
        return FavortieDto.customMapping(favorite);
    }

    @Override
    public List<FavortieDto> getAllByAccountId(Long accountId) {

        List<Favorite> favoriteList = favoriteRepository.findAllByAccountId(accountId)
                .orElseThrow(()-> new EntityNotFoundException("favoriteList not found!"));
        return FavortieDto.customListMapping(favoriteList);
    }
}

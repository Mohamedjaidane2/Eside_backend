package com.eside.account.dtos.FavoritesDtos;

import com.eside.account.model.Account;
import com.eside.account.model.Favorite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FavortieDto {

    private Long id;
    private Date creationDate;
    private Long advertismentId ;
    private Long accountId ;

    public static FavortieDto customMapping(Favorite favorite) {
        return FavortieDto.builder()
                .id(favorite.getId())
                .creationDate(favorite.getCreationDate())
                .advertismentId(favorite.getAdvertismentId())
                .accountId(favorite.getAccount().getId())
                .build();
    }

    public static List<FavortieDto> customListMapping(List<Favorite> favorites){
        if (favorites == null) return null;
        ArrayList<FavortieDto> favortieDtoArrayList = new ArrayList<>();
        for (Favorite favorite : favorites) {
            FavortieDto favortieDto = customMapping(favorite);
            favortieDtoArrayList.add(favortieDto);
        }
        return favortieDtoArrayList;
    }
}
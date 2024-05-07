package com.eside.account.controller;

import com.eside.account.dtos.FavoritesDtos.FavortieDto;
import com.eside.account.dtos.FavoritesDtos.NewFavoriteDto;
import com.eside.account.dtos.FeedBackDtos.FeedBackNewDto;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.service.impl.AccountServiceImpl;
import com.eside.account.service.impl.FavoriteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/favorite")
@RestController
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoriteServiceImpl favoriteService;

    @PostMapping("/add")
    public ResponseEntity<SuccessDto> create (@RequestBody NewFavoriteDto newFavoriteDto) {
        return ResponseEntity.ok(favoriteService.addToFavorite(newFavoriteDto));
    }
    @PostMapping("/remove")
    public ResponseEntity<SuccessDto> deleteFromFavorite (@RequestBody NewFavoriteDto newFavoriteDto) {
        return ResponseEntity.ok(favoriteService.deleteFromFavorite(newFavoriteDto));
    }

    @PostMapping("/is-selected")
    public ResponseEntity<Boolean> isSelected (@RequestBody NewFavoriteDto newFavoriteDto) {
        return ResponseEntity.ok(favoriteService.isSelected(newFavoriteDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<FavortieDto> getFavoriteById(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteService.getFavoriteById(id));
    }


    @GetMapping("/get-all/by-account/{id}")
    public ResponseEntity<List<FavortieDto>> getAllByAccountId(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteService.getAllByAccountId(id));
    }
}

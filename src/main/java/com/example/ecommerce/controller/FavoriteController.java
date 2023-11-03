package com.example.ecommerce.controller;

import com.example.ecommerce.model.dto.request.FavoriteDeleteRequest;
import com.example.ecommerce.model.dto.request.FavoriteRequest;
import com.example.ecommerce.model.dto.response.FavoriteResponse;
import com.example.ecommerce.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")

public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/addFavorite")
    public ResponseEntity<?> addFavorite (@RequestHeader("Authorization") String token,@RequestBody FavoriteRequest favoriteRequest)
    {
        return favoriteService.addFavorite(token , favoriteRequest);
    }
    @GetMapping("/listFavorite")
    public Map<String,Object> listFavorite(@RequestHeader("Authorization") String token)
    {
        return favoriteService.listFavorite(token);
    }

    @DeleteMapping("/deleteFavorite")
    public ResponseEntity<?> deleteFavorite(@RequestHeader("Authorization") String token,@RequestBody FavoriteDeleteRequest favoriteDeleteRequest){
        return favoriteService.deleteFavoriteProduct(token,favoriteDeleteRequest);
    }
}

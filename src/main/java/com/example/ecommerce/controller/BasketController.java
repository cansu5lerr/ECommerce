package com.example.ecommerce.controller;

import com.example.ecommerce.model.dto.request.BasketDeleteRequest;
import com.example.ecommerce.model.dto.request.BasketRequest;
import com.example.ecommerce.model.dto.response.BasketResponse;
import com.example.ecommerce.repository.BasketRepository;
import com.example.ecommerce.service.BasketService;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class BasketController {
    @Autowired
    BasketService basketService;

    @PostMapping("/addBasket")
    public ResponseEntity<?> addBasket(@RequestHeader("Authorization") String token , @RequestBody BasketRequest basketRequest) {
        return basketService.addBasket(token,basketRequest);
    }

    @GetMapping("/listBasket")
    public Map<String, Object> listBasket(@RequestHeader("Authorization") String token){
        return basketService.listBasket(token);
    }

    @DeleteMapping("/deleteBasket")
    public ResponseEntity<?> deleteBaskeT(@RequestHeader("Authorization")String token, @RequestBody BasketDeleteRequest basketDeleteRequest){
        return basketService.deleteProductByBasket(token,basketDeleteRequest);
    }
}

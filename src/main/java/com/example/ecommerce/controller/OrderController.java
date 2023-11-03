package com.example.ecommerce.controller;

import com.example.ecommerce.model.dto.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.service.OrderService;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class OrderController {


    @Autowired
    OrderService orderService;
    @PostMapping("/addOrder")
    public Map<String, Object> addOrder (@RequestHeader("Authorization") String token, @RequestBody OrderRequest orderRequest)
    {
        return orderService.addProduct(token,orderRequest);
    }
}

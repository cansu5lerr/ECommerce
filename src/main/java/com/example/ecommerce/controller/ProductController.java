package com.example.ecommerce.controller;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.exception.UnauthorizedUserException;
import com.example.ecommerce.model.dto.request.ProductRequest;
import com.example.ecommerce.model.dto.response.ProductResponse;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")

public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/addProduct")
    public ProductResponse addProduct(@RequestHeader("Authorization") String token , @RequestBody ProductRequest productRequest) throws UnauthorizedUserException {
        ProductResponse productResponse = productService.addProduct(token,productRequest);
        return productResponse;
    }

    @GetMapping("/{id}")
    public List<ProductResponse> listProduct(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) throws ProductException, UnauthorizedUserException {
        return productService.listProduct(token, id);
    }

}

package com.example.ecommerce.service;

import com.example.ecommerce.model.dto.request.BasketDeleteRequest;
import com.example.ecommerce.model.dto.request.BasketRequest;
import com.example.ecommerce.model.dto.response.BasketResponse;
import com.example.ecommerce.model.dto.response.MessageResponse;
import com.example.ecommerce.model.entity.Basket;
import com.example.ecommerce.repository.BasketRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.entity.User;
import com.example.ecommerce.model.entity.Product;
import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor

public class BasketService {
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    ProductRepository productRepository;
    public ResponseEntity<?> addBasket(String token, BasketRequest basketRequest) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if (authenticatedUser != null) {
            Basket basket = authenticatedUser.getBasket();
            if (basket == null) {
                basket = new Basket();
                basket.setUser(authenticatedUser);
            }
            List<Product> productList = new ArrayList<>();
            for(Long id : basketRequest.getProductIdList()) {
                try{
                    Product products = productRepository.findById(id).orElseThrow();
                    productList.add(products);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
                }
            }
            for (Product product : basket.getProductList()) {
                productList.add(product);
            }
            basket.setProductList(productList);
            basketRepository.save(basket);
            return ResponseEntity.ok(new MessageResponse("Ürünler sepete eklendi!"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Kimlik doğrulama hatası."));
    }

    public Map<String, Object>  listBasket (String token) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        Map<String, Object> response = new HashMap<>();
        if(authenticatedUser instanceof User) {
            Basket basket = authenticatedUser.getBasket();
            if(basket == null) {
                response.put("message", "Sepetiniz boş!");
            }
            BigDecimal totalPrice = BigDecimal.ZERO;
            List <BasketResponse> basketResponses = new ArrayList<>();
            List<Product> productList = basket.getProductList();
            for(Product product : productList) {
                BasketResponse basketResponse = new BasketResponse();
                BigDecimal price = product.getProductPrice();
                totalPrice = totalPrice.add(price);
                basketResponse.setProductName(product.getProductName());
                basketResponse.setProductId(product.getId());
                basketResponse.setPrice(product.getProductPrice());
                basketResponses.add(basketResponse);
            }
            response.put("products", basketResponses);
            response.put("totalPrice", totalPrice);
            response.put("totalSize", productList.size());
            return response;
        }
        response.put("message", "Kimlik doğrulama hatası.");
        return response;
    }

    public ResponseEntity<?> deleteProductByBasket(String token, BasketDeleteRequest basketRequest) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if(authenticatedUser instanceof User) {
            Basket basket = authenticatedUser.getBasket();
            Long productId = basketRequest.getProductId();
            List <Product> productList = basket.getProductList();
            Optional<Product> productToRemove = productList.stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst();
            if(productToRemove.isPresent()) {
                productList.remove(productToRemove.get());
                basket.setProductList(productList);
                basketRepository.save(basket);
                return ResponseEntity.ok(new MessageResponse("Ürün sepetten kaldırıldı"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ürün bulunamadı."));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Kimlik doğrulama hatası."));
    }


}

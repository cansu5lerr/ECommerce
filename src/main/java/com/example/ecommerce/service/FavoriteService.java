package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.dto.request.FavoriteDeleteRequest;
import com.example.ecommerce.model.dto.request.FavoriteRequest;
import com.example.ecommerce.model.dto.response.FavoriteResponse;
import com.example.ecommerce.model.dto.response.MessageResponse;
import com.example.ecommerce.model.entity.Favorite;
import com.example.ecommerce.model.entity.Product;
import com.example.ecommerce.model.entity.User;
import com.example.ecommerce.repository.FavoriteRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor

public class FavoriteService {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    FavoriteRepository favoriteRepository;
    ProductRepository productRepository;

    public ResponseEntity<?> addFavorite (String token , FavoriteRequest favoriteRequest) {
       User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
       if(authenticatedUser instanceof User) {
           Favorite favorite = authenticatedUser.getFavorite();
           if(favorite == null ) {
               favorite = new Favorite();
               favorite.setUser(authenticatedUser);
           }
           List <Product> productList = favorite.getProductList();
           List <FavoriteResponse> favoriteResponses = new ArrayList<>();

            boolean isDigitFavorite = false;
            favorite.getProductList();
           for(Long id : favoriteRequest.getProductIdList()) {
               try {
                   for(Product product : favorite.getProductList()) {
                       long productId = product.getId();
                       if(id.equals(productId)){
                           isDigitFavorite = true;
                           break;
                       }
                   }
                   if(isDigitFavorite) {
                       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu ürün zaten favorilerde mevcut.");
                   }
                   else{
                   Product products = productRepository.findById(id).orElseThrow();
                   productList.add(products);
                   FavoriteResponse favoriteResponse = new FavoriteResponse();
                   favoriteResponse.setProductPrice(products.getProductPrice());
                   favoriteResponse.setProductName(products.getProductName());
                   favoriteResponse.setProductId(products.getId());
                   favoriteResponses.add(favoriteResponse);
                       favorite.setProductList(productList);
                       favoriteRepository.save(favorite);
                      return ResponseEntity.ok(favoriteResponses);
                   }
               }
         catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ürün bulunamadı.");
         }
           };

       }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı bulunamadı.");
    }
    public Map<String,Object> listFavorite (String token) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        Map<String, Object> response = new HashMap<>();
        if(authenticatedUser instanceof User) {
            Favorite favorite = authenticatedUser.getFavorite();
            if(favorite==null) {
                return null;
            }

            List<FavoriteResponse> responses = favorite.getProductList().stream().
                    map(product -> {
                        FavoriteResponse  favoriteResponse = new FavoriteResponse();
                        favoriteResponse.setProductId(product.getId());
                        favoriteResponse.setProductName(product.getProductName());
                        favoriteResponse.setProductPrice(product.getProductPrice());
                       return favoriteResponse;
                    }
            ).collect(Collectors.toList());
            response.put("products", responses);
            response.put("totalSize", responses.size());
            return response;
        }
        return null;
    }

    public ResponseEntity<?> deleteFavoriteProduct (String token, FavoriteDeleteRequest favoriteDeleteRequest) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if(authenticatedUser instanceof User) {
            Favorite favorite = authenticatedUser.getFavorite();
            Long productId = favoriteDeleteRequest.getProductId();
            List<Product> productList = favorite.getProductList();
            Optional<Product> productToRemove = productList.stream()
                    .filter(product-> product.getId().equals(productId))
                    .findFirst();
            if(productToRemove.isPresent()) {
                productList.remove(productToRemove.get());
                favorite.setProductList(productList);
                favoriteRepository.save(favorite);
                return ResponseEntity.ok(new MessageResponse("Ürün sepetten kaldırıldı."));
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ürün bulunamadı."));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Kimlik doğrulama hatası."));
    }

}

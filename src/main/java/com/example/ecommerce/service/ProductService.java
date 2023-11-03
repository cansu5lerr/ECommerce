package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.exception.UnauthorizedUserException;
import com.example.ecommerce.model.dto.request.ProductRequest;
import com.example.ecommerce.model.dto.response.CategoryResponse;
import com.example.ecommerce.model.dto.response.ProductResponse;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    CategoryRepository categoryRepository;


    //add Product

    public ProductResponse addProduct (String token, ProductRequest productRequest) throws UnauthorizedUserException {
        User authenticedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if(authenticedUser instanceof User) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow();
            Product product = Product.builder()
                    .productPrice(productRequest.getProductPrice())
                    .productName(productRequest.getProductName())
                    .productAmount(productRequest.getProductAmount())
                    .category(category)
                    .build();
            productRepository.save(product);
        return getProductResponse(product,category);
        }
        throw new UnauthorizedUserException("Kimlik doğrulama başarısız.");
    }
    public List<ProductResponse> listProduct (String token, Long categoryId) throws ProductException, UnauthorizedUserException {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if(authenticatedUser instanceof User) {
            List <Product> products = productRepository.findAll();
            List<ProductResponse> productResponses = products.stream()
                    .filter(product -> product.getCategory().getId() == categoryId)
                    .map(product -> {
                        ProductResponse productResponse = new ProductResponse();
                        productResponse.setProductAmount(product.getProductAmount());
                        productResponse.setProductName(product.getProductName());
                        productResponse.setProductPrice(product.getProductPrice());
                        productResponse.setCategoryId(categoryId);
                        productResponse.setProductId(product.getId());
                        return productResponse;
                    }).collect(Collectors.toList());
            if(!productResponses.isEmpty()){
              return productResponses;
            }
            else {
            throw new ProductException("Bu kategoriye ait ürün bulunmamaktadır");
            }


        }
        throw new UnauthorizedUserException("Kimlik doğrulama başarısız.");
    }

    private ProductResponse getProductResponse (Product savedProduct, Category category) {
        return ProductResponse.builder()
                .categoryId(category.getId())
                .productName(savedProduct.getProductName())
                .productPrice(savedProduct.getProductPrice())
                .productAmount(savedProduct.getProductAmount())
                .build();

    }
}

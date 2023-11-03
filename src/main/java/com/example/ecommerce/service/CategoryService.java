package com.example.ecommerce.service;

import com.example.ecommerce.exception.CategoryCreationException;
import com.example.ecommerce.exception.CategoryListException;
import com.example.ecommerce.exception.UnauthorizedUserException;
import com.example.ecommerce.model.dto.response.CategoryResponse;
import com.example.ecommerce.model.dto.request.CategoryRequest;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.entity.User;
import com.example.ecommerce.model.entity.Category;
import org.yaml.snakeyaml.tokens.ScalarToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    UserDetailsServiceImpl userDetailsService;

    public CategoryResponse addCategory(String token, CategoryRequest categoryRequest) throws CategoryCreationException, UnauthorizedUserException {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if (authenticatedUser instanceof User) {
            Category category = Category.builder().categoryName(categoryRequest.getCategoryName()).build();
            try {
                categoryRepository.save(category);
                return getCategoryResponse(category);
            } catch (Exception e) {
                throw new CategoryCreationException("Kategori eklenemedi.", e);
            }
        } else {
            throw new UnauthorizedUserException("Kimlik doğrulama başarısız.");
        }
    }

    public List<CategoryResponse> listCategory(String token) throws CategoryListException, UnauthorizedUserException {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if (authenticatedUser instanceof User) {
            try {
                List <Category> categories = categoryRepository.findAll();
                List<CategoryResponse> categoryList = categories.stream()
                        .map(category -> {
                            CategoryResponse categoryResponse = new CategoryResponse();
                            categoryResponse.setCategoryName(category.getCategoryName());
                            categoryResponse.setId(category.getId());
                            return categoryResponse;
                        }).collect(Collectors.toList());
                return categoryList;
            } catch (Exception e) {
                throw new CategoryListException("Kategori listelenemedi.", e);

            }
        }
        throw new UnauthorizedUserException("Kimlik doğrulama başarısız.");
    }

    public ResponseEntity<String> deleteCategory (Long id , String token){
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setIsActive(false);
        categoryRepository.save(category);
        return ResponseEntity.ok(id +" idli kategori silinmiştir.");
    }


    private CategoryResponse getCategoryResponse (Category savedCategory) {
        return CategoryResponse.builder()
                .id(savedCategory.getId())
                .categoryName(savedCategory.getCategoryName())
                .build();

    }

}

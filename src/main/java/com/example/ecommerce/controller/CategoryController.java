package com.example.ecommerce.controller;

import com.example.ecommerce.exception.CategoryCreationException;
import com.example.ecommerce.exception.CategoryListException;
import com.example.ecommerce.exception.UnauthorizedUserException;
import com.example.ecommerce.model.dto.request.CategoryRequest;
import com.example.ecommerce.model.dto.response.CategoryResponse;
import com.example.ecommerce.model.entity.Category;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping("/addCategory")
    public CategoryResponse addCategory(@RequestHeader("Authorization") String token , @RequestBody CategoryRequest categoryRequest) throws UnauthorizedUserException, CategoryCreationException {
        CategoryResponse categoryResponse = categoryService.addCategory(token,categoryRequest);
        return categoryResponse;
    }
    @GetMapping("/listCategory")
    public List<CategoryResponse> listCategory(@RequestHeader("Authorization") String token) throws CategoryListException, UnauthorizedUserException {
        List <CategoryResponse> categoryResponses = categoryService.listCategory(token);
        return categoryResponses;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory (@RequestHeader("Authorization") String token , @PathVariable Long id) {
       ResponseEntity<String> response = categoryService.deleteCategory(id,token);
        return response;
    }
}

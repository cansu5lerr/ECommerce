package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.model.entity.Category;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    @Override
    Optional<Category> findById(Long id);
}

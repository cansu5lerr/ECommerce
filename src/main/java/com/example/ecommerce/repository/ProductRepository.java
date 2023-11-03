package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.model.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(Long categoryId);
    @Query("SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(concat('%', :productName, '%'))")
    List<Product> findByNameWithQuery(@Param("productName") String productName);

}

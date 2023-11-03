package com.example.ecommerce.repository;

import com.example.ecommerce.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.model.entity.Category;

import java.util.Optional;

public interface BasketRepository  extends JpaRepository<Basket, Long> {

}

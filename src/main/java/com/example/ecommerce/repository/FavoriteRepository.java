package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.model.entity.Favorite;
@Repository
public interface FavoriteRepository  extends JpaRepository<Favorite,Long> {
}

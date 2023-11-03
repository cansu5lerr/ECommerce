package com.example.ecommerce.repository;

import java.util.Optional;


import com.example.ecommerce.model.entity.User;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@NonNullApi

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @NonNull
    Optional<User> findByUsername(String username);

    @NonNull
    Optional<User>  findById(Long id);
    @NonNull
    Boolean existsByUsername(String username);
    @NonNull
    Boolean existsByEmail(String email);


}
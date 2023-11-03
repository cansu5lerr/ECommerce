package com.example.ecommerce.repository;
import java.util.Optional;

import com.example.ecommerce.model.entity.ERole;
import com.example.ecommerce.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
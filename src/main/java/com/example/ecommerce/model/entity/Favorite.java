package com.example.ecommerce.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_product",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;
}
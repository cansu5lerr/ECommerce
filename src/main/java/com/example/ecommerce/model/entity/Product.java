 package com.example.ecommerce.model.entity;

import jakarta.persistence.*;

import lombok.ToString;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause ="is_active = true")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "product_id")
    private Long id;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_price")
    private BigDecimal productPrice;

    @Column(name="product_amount")
    private Long productAmount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @Where(clause = "category.is_active = true")
    private Category category;


    @Builder.Default
    @Column(name="is_active")
    private Boolean isActive = true;


}

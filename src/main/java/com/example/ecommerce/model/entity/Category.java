package com.example.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "category_name")
})
@Where(clause ="is_active = true")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;
    @Column(name="category_name")
    private String categoryName;
    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Product> products;
    @Builder.Default
    @Column(name="is_active")
    private Boolean isActive = true;
}

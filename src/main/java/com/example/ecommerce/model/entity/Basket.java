// Basket.java
package com.example.ecommerce.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // Değişken adı ve sütun adını uyumlu hale getirin
    private User user;

    @ManyToMany(fetch = FetchType.LAZY) // Birden çok ürünün sepete eklenmesi durumunda ManyToMany kullanılabilir
    @JoinTable(
            name = "basket_product", // İlişkiyi temsil eden tablo
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    @Column(name = "totalPrices")
    private BigDecimal totalPrice;
}

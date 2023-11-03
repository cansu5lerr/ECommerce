package com.example.ecommerce.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteResponse {
    private String productName;
    private BigDecimal productPrice;
    private Long productId;
}

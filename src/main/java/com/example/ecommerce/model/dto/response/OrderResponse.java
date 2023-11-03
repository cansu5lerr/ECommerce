package com.example.ecommerce.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private BigDecimal price;
    private Long productId;
    private String productName;
}

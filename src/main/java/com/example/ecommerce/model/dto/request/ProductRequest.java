package com.example.ecommerce.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String productName;
    private Long categoryId;
    private BigDecimal productPrice;
    private Long productAmount;
}

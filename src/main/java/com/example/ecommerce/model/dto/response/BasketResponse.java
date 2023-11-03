package com.example.ecommerce.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketResponse {

    private String productName;
    private Long productId;
    private BigDecimal price;
}

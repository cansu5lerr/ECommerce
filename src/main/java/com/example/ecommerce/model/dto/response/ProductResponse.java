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

public class ProductResponse {
        private String productName;
        private Long categoryId;
        private BigDecimal productPrice;
        private Long productAmount;
        private Long productId;

}


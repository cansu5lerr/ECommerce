package com.example.ecommerce.model.dto.request;
import com.example.ecommerce.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class BasketRequest {
   //     private List<Product> product;
        private List<Long> productIdList;
    }


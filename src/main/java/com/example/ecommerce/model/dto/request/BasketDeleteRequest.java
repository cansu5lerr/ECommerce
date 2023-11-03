package com.example.ecommerce.model.dto.request;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketDeleteRequest {
    private Long productId;
}

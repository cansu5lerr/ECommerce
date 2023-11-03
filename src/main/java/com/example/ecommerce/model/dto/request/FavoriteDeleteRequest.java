package com.example.ecommerce.model.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FavoriteDeleteRequest {
    private Long productId;
}

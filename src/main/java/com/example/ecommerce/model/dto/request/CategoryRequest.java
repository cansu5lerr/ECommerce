package com.example.ecommerce.model.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String categoryName;
}

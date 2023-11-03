package com.example.ecommerce.model.dto.request;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateRequest {
    private Long id;
    private String categoryName;
}

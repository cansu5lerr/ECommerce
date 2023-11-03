package com.example.ecommerce.model.dto.response;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String categoryName;
    private Long id;
}

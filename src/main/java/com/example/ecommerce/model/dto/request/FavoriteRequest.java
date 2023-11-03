package com.example.ecommerce.model.dto.request;

import com.example.ecommerce.model.entity.Product;
import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteRequest {

    private List<Long> productIdList;
}
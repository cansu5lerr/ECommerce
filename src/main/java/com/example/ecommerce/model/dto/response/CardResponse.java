package com.example.ecommerce.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardResponse {
    private String name;
    private String surname;
    private BigDecimal budget;
    private LocalDateTime date;
    private String CvCode;
}

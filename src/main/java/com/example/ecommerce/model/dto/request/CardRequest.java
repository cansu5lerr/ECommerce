package com.example.ecommerce.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    private String CardNumber;
    private BigDecimal budget;
    private String CVCode;

}

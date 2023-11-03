/*package com.example.ecommerce.service;
import com.example.ecommerce.model.dto.request.BasketItemRequest;
import com.example.ecommerce.model.dto.response.BasketItemResponse;
import com.example.ecommerce.repository.BasketItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.entity.BasketItem;
import java.util.List;

@Service
@AllArgsConstructor
public class BasketItemService {
    @Autowired

    public List<BasketItem> findAll(List<Long> basketItemIds) {
        return basketItemRepository.findAllById(basketItemIds);
    }
    public BasketItem findById(Long basketItemId) {
        return basketItemRepository.findById(basketItemId).orElseThrow();

    }
    public BasketItemResponse getBasketItemResponse(BasketItem basketItem) {
        return BasketItemResponse.builder()
                .id(basketItem.getId())
                .basketId(basketItem.getBasket().getId())
                .quantity(basketItem.getQuantity())
                .productId(basketItem.getProduct().getId())
                .build();
    }
}

 */

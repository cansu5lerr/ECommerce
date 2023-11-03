package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.dto.request.OrderRequest;
import com.example.ecommerce.model.dto.response.MessageResponse;
import com.example.ecommerce.model.dto.response.OrderResponse;
import com.example.ecommerce.model.entity.Order;
import com.example.ecommerce.model.entity.User;
import com.example.ecommerce.repository.BasketRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.ecommerce.model.entity.Basket;
@Service
@AllArgsConstructor
public class OrderService {
    @Autowired
     UserDetailsServiceImpl userDetailsService;
    @Autowired
     BasketRepository basketRepository;
    @Autowired
     OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    public Map<String, Object> addProduct(String token, OrderRequest orderRequest) {
        User authtenticatedUser= userDetailsService.getAuthenticatedUserFromToken(token);
        Map<String,Object> response = new HashMap<>();
        List<OrderResponse> responseList = new ArrayList<>();
        if(authtenticatedUser instanceof User) {
            Order order = authtenticatedUser.getOrder();
            if(order == null) {
                order = new Order();
                order.setUser(authtenticatedUser);
            }
            Basket basket = authtenticatedUser.getBasket();
            BigDecimal totalPrice = BigDecimal.ZERO;
            List<Product> productListToOrder =new ArrayList<>();
            List<Product> productListByOrder = new ArrayList<>();
            for(Long id : orderRequest.getProductIdList()){
                Product product = productRepository.findById(id).orElseThrow();
                long newAmount = product.getProductAmount()-1;
                product.setProductAmount(newAmount);
                productRepository.save(product);
                productListByOrder.add(product);
            }
            for (Product product :productListByOrder) {
            Product matchingProduct = findMatchingProductInBasket(product,basket);
             if(matchingProduct != null) {
               productListToOrder.add(matchingProduct);
               OrderResponse orderResponse = creatOrderResponse(matchingProduct);
               basket.getProductList().remove(matchingProduct);
               responseList.add(orderResponse);
               totalPrice = totalPrice.add(matchingProduct.getProductPrice());
           }
            }
            LocalDateTime now = LocalDateTime.now();
            order.setTotalPrice(totalPrice);
            order.setCreatedDate(now);
            order.setProductList(productListToOrder);
            basketRepository.save(basket);
            orderRepository.save(order);
            response.put("Toplam Ücret:", totalPrice);
            response.put("Miktar:" ,responseList.size());
            response.put("Ürünler", responseList);
            return response;
        }
        return null;
    }

    private Product findMatchingProductInBasket(Product product, Basket basket){
        for (Product productInBasket : basket.getProductList()){
            if(Objects.equals(productInBasket.getId(), product.getId())) {
        return productInBasket;
            }
        }
        return null;
    }

    private OrderResponse creatOrderResponse(Product product) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setProductName(product.getProductName());
        orderResponse.setProductId(product.getId());
        orderResponse.setPrice(product.getProductPrice());
        return orderResponse;
    }

}

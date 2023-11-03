package com.example.ecommerce.service;

import com.example.ecommerce.model.dto.request.BudgetAddRequest;
import com.example.ecommerce.model.dto.request.CardRequest;
import com.example.ecommerce.model.dto.response.CardResponse;
import com.example.ecommerce.model.dto.response.UserDetailsResponse;
import com.example.ecommerce.model.entity.Card;
import com.example.ecommerce.model.entity.User;
import com.example.ecommerce.repository.CardRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
   @Autowired
    UserRepository userRepository;
    public CardResponse createCard (String token , CardRequest cardRequest) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if(authenticatedUser instanceof User) {
            Card card = new Card();
            card.setCardNumber(card.getCardNumber());
            card.setUser(authenticatedUser);
            card.setCVCode(cardRequest.getCVCode());
            LocalDateTime date = LocalDateTime.now();
            card.setCreatedDate(date);
            card.setName(authenticatedUser.getName());
            card.setSurname(authenticatedUser.getSurname());
            card.setBudget(cardRequest.getBudget());
            cardRepository.save(card);
            userRepository.save(authenticatedUser);
            CardResponse cardResponse = new CardResponse();
            cardResponse.setDate(date);
            cardResponse.setBudget(cardRequest.getBudget());
            cardResponse.setName(authenticatedUser.getName());
            cardResponse.setSurname(authenticatedUser.getSurname());
            cardResponse.setCvCode(cardRequest.getCVCode());
            return cardResponse;
        }
        return null;
    }
    public Map<String, BigDecimal> showBudget(String token) {
        User authenticatedUser= userDetailsService.getAuthenticatedUserFromToken(token);
        Map <String, BigDecimal> hashMap = new HashMap<>();

        if(authenticatedUser instanceof User)
        {
            Card card = authenticatedUser.getCard();
            BigDecimal budget = card.getBudget();
            hashMap.put("Bakiye", budget);
            return hashMap;
        }
        return null;

    }
    public Map<String, BigDecimal> addBudget (String token, BudgetAddRequest budgetAddRequest ) {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        Map<String , BigDecimal> hashMap = new HashMap<>();
        if(authenticatedUser instanceof User) {
            Card card = authenticatedUser.getCard();
            BigDecimal budget = card.getBudget();
            BigDecimal additionalBudget = budgetAddRequest.getBudget();
            BigDecimal newBudget = budget.add(additionalBudget);
            card.setBudget(newBudget);
            cardRepository.save(card);
            authenticatedUser.setCard(card);
            userRepository.save(authenticatedUser);
            hashMap.put("Yeni Bakiye",newBudget );
            return hashMap;
        }

        return null;
    }

    public String deleteCard (String token, CardRequest cardRequest)
    {
        User authenticatedUser = userDetailsService.getAuthenticatedUserFromToken(token);
        if(authenticatedUser instanceof  User ) {
            Card card = authenticatedUser.getCard();
            if (card.getCardNumber().equals(cardRequest.getCardNumber())) {
                cardRepository.delete(card);
                return "Card silindi";
            }
        }
        return "Kullanıcı bulunamadı";
    }
}

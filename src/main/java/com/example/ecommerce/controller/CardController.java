package com.example.ecommerce.controller;

import com.example.ecommerce.model.dto.request.BudgetAddRequest;
import com.example.ecommerce.model.dto.request.CardRequest;
import com.example.ecommerce.model.dto.response.CardResponse;
import com.example.ecommerce.model.entity.Card;
import com.example.ecommerce.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class CardController {
    @Autowired
    CardService cardService;
    @PostMapping("/createCard")
    public CardResponse createCard(@RequestHeader("Authorization") String token, @RequestBody CardRequest cardRequest) {
        CardResponse cardResponse = cardService.createCard(token,cardRequest);
        return cardResponse;
    }

    @GetMapping("/showCard")
    public Map<String, BigDecimal> showBudget(@RequestHeader("Authorization") String token) {
        return cardService.showBudget(token);
    }

    @PatchMapping("/addBudget")
    public Map<String, BigDecimal> addBudget (@RequestHeader("Authorization") String token , @RequestBody  BudgetAddRequest addRequest) {
        return cardService.addBudget(token,addRequest);
    }
    @DeleteMapping("/deleteCard")
    public String deleteCard (@RequestHeader("Authorization") String token, @RequestBody CardRequest cardRequest){
        return cardService.deleteCard(token, cardRequest);
    }
}

package com.example.ecommerce.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="card_id", nullable= false)
    private Long id;

    @Column(name ="card_num")
    private String cardNumber;

    @Column(name="user_name")
    private String name;
    @Column(name="user_surname")
    private String surname;
    @Column(name= "created_Date")
    private LocalDateTime createdDate;

    @Column(name="cv_Code")
    private String CVCode;

    @Column(name="card_budget")
    private BigDecimal budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "card_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private User user;



}

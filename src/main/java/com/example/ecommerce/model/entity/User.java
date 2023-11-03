package com.example.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames= "email")
})

@Where(clause ="is_active = true")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max=120)
    private String name;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NotBlank
    @Size(max=120)
    private String surname;
    @NotBlank
    @Size(max=120)
    private String username;
    @NotBlank
    @Size(min=6, max=120)
    private String password;
    @NotBlank
    @Email
    private String email;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Order order;
    public Order getOrder() {return order;}
    public void setOrder(Order order) {this.order= order;}

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn
    private Favorite favorite;

    @OneToOne(mappedBy ="user", cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Card card;
    public Card getCard() {return card;}
    public void setCard(Card card) {this.card = card;}

    public Favorite getFavorite(){return favorite;}
    public void setFavorite (Favorite favorite) {this.favorite = favorite;}
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
   // @JoinTable(name = "basket_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private Basket basket;
    public Basket getBasket() {return basket;}
    public void setBasket(Basket basket) {this.basket= basket;}

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public User() {}

    public User(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
    @Builder.Default
    @Column(name= "is_active")
    private Boolean isActive = true;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}

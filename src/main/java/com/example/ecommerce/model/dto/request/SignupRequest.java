package com.example.ecommerce.model.dto.request;

import com.example.ecommerce.model.entity.Basket;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank
    @Size(min= 3, max= 20)
    private String username;
    @NotBlank
    @Size(min=3, max=20)
    private String name;
    @Size(min=3, max=20)
    @NotBlank
    private String surname;
    @NotBlank
    @Size(min = 3, max = 40)
    private String password;
    @NotBlank
    @Email
    private String email;

    private Basket basket;

    private Set<String> role;
    public Basket getBasket() {return basket;}
    public void setBasket(Basket basket) {this.basket=basket;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Set<String> getRole() {
        return this.role;
    }
    public void setRole(Set<String> role) {
        this.role = role;
    }
}

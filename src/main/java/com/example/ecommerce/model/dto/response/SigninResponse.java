package com.example.ecommerce.model.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SigninResponse {
    @NotBlank
    @Size(min= 3, max= 20)
    private String username;

    @NotBlank
    @Email
    private String email;
    private Set<String> role;

}

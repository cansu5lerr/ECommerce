package com.example.ecommerce.service;

import com.example.ecommerce.model.dto.request.LoginRequest;
import com.example.ecommerce.model.dto.request.SignupRequest;
import com.example.ecommerce.model.dto.response.JwtResponse;
import com.example.ecommerce.model.dto.response.MessageResponse;
import com.example.ecommerce.model.entity.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.jwt.JwtUtils;
import com.example.ecommerce.service.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service

public class AuthService implements UserDetailsService  {
        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UserRepository userRepository;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        JwtUtils jwtUtils;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return null;
        }
        @Transactional
        public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity
                    .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
        }

        public ResponseEntity<?> registerUser (SignupRequest signupRequest){
            if (userRepository.existsByUsername(signupRequest.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            // Create new user's account
            User user = new User(signupRequest.getUsername(), signupRequest.getName(), signupRequest.getSurname(), signupRequest.getEmail(),
                    encoder.encode(signupRequest.getPassword()));

            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "mod":
                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);

                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }




            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }

    }

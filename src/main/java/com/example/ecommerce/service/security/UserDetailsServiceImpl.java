package com.example.ecommerce.service.security;


import com.example.ecommerce.model.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.jwt.JwtUtils;
import com.example.ecommerce.model.dto.response.UserDetailsResponse;
import com.example.ecommerce.service.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
    @Transactional
    public UserDetails loadUserByUserid(Long username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public UserDetailsResponse getUserProfile (String token){
        String userid = jwtUtils.extractId(token);
        UserDetailsResponse userDetatilsResponse = new UserDetailsResponse();
        UserDetails userDetails = null;
        if(!userid.isEmpty()) {
            userDetails = loadUserByUserid(Long.parseLong(userid));
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> userOptional = userRepository.findById(Long.parseLong(userid));
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                userDetatilsResponse.setName(user.getName());
                userDetatilsResponse.setSurname(user.getSurname());
                userDetatilsResponse.setUsername(user.getUsername());
                userDetatilsResponse.setEmail(user.getEmail());
            }
        }
        return userDetatilsResponse;
    }
    //User güncellemesi
    public UserDetailsResponse updateUserProfile(String token , UserDetailsResponse userDetailsResponse) {
        String userid = jwtUtils.extractId(token);
        UserDetails userDetails = null;
        if(userid != null) {
            userDetails = loadUserByUserid(Long.parseLong(userid));
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> userOptional = userRepository.findById(Long.parseLong(userid));
            if(userOptional.isPresent()){
                User user = userOptional.get();
                user.setId(user.getId());
                user.setPassword(user.getPassword());
                user.setUsername(user.getUsername());
                user.setName(userDetailsResponse.getName());
                user.setSurname(userDetailsResponse.getSurname());
                user.setEmail(userDetailsResponse.getEmail());
                user.setRoles(user.getRoles());
                userRepository.save(user);
            }
        }
        return userDetailsResponse;
    }


    //burada kullanıcı authentication işlemini yapıyorum

    public User getAuthenticatedUserFromToken(String token) {
        String userid = jwtUtils.extractId(token);
        UserDetails userDetails = null;

        if (userid != null) {
            userDetails = loadUserByUserid(Long.parseLong(userid));
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> userOptional = userRepository.findById(Long.parseLong(userid));
            if (userOptional.isPresent()) {
                return userOptional.get();
            }
        }

        return null;
    }

}
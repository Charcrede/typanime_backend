package com.typanime.typanime_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typanime.typanime_backend.model.User;
import com.typanime.typanime_backend.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user")
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        User user = userRepository.findByUsername(authentication.getName());
        // return "{id : " + user.getId() +" username : " + user.getUsername() + ", email : " + user.getEmail() + " }";
        return user;
    }
}

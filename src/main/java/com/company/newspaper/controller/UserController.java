package com.company.newspaper.controller;

import com.company.newspaper.model.web.LoginRequest;
import com.company.newspaper.model.web.RegistrationRequest;
import com.company.newspaper.model.entities.User;
import com.company.newspaper.model.entities.UserSession;
import com.company.newspaper.repository.UserRepository;
import com.company.newspaper.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired UserRepository userRepository;
    @Autowired UserSessionRepository userSessionRepository;


    @PostMapping("/register")
    public UserSession register(@RequestBody RegistrationRequest request) {

        User user = userRepository.createUser(request);
        return userSessionRepository.create(
                UUID.randomUUID().toString(),
                user);
    }

    @PostMapping("/login")
    public UserSession login(@RequestBody LoginRequest request) {
        User user = userRepository.getByUsernameAndPassword(
                request.getUsername(),
                request.getPassword());

        if (user == null) {
            throw new UsernameNotFoundException("Login is incorrect");
        }

        return userSessionRepository.create(
                UUID.randomUUID().toString(),
                user
        );
    }

    @PutMapping("/logout")
    public void logout(@RequestHeader("Authorization") String sessionId) {
        userSessionRepository.invalidateSession(sessionId);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable Integer id){
        return userRepository.getByUserId(id);
    }

    @PutMapping("/promote")
    public User promote(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "role") Integer roleIndex) {
        return userRepository.promote(username, roleIndex);
    }

    @PutMapping("/demote")
    public User demote(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "role") Integer roleIndex) {
        return userRepository.demote(username, roleIndex);
    }
}

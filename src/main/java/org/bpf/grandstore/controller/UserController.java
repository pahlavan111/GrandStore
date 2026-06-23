package org.bpf.grandstore.controller;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.entity.User;
import org.bpf.grandstore.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers(){
       return userRepository.findAll();
    }
}

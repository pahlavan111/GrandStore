package org.bpf.grandstore.service;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.entity.User;
import org.bpf.grandstore.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

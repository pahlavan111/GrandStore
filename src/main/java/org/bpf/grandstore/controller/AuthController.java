package org.bpf.grandstore.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.JwtResponse;
import org.bpf.grandstore.dto.LoginRequestBody;
import org.bpf.grandstore.dto.UserDto;
import org.bpf.grandstore.mapper.UserMapper;
import org.bpf.grandstore.repository.UserRepository;
import org.bpf.grandstore.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequestBody requestBody
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestBody.getEmail(),
                        requestBody.getPassword()
                )
        );

        var user = userRepository.findByEmail(requestBody.getEmail()).orElseThrow();

        var token = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        var user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

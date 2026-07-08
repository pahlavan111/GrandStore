package org.bpf.grandstore.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.JwtResponse;
import org.bpf.grandstore.dto.LoginRequestBody;
import org.bpf.grandstore.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

        var token = jwtService.generateToken(requestBody.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }
}

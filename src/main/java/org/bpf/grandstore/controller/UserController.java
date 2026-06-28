package org.bpf.grandstore.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.ChangePasswordRequest;
import org.bpf.grandstore.dto.UpdateUserRequest;
import org.bpf.grandstore.dto.UserDto;
import org.bpf.grandstore.dto.UserDtoRequest;
import org.bpf.grandstore.entity.User;
import org.bpf.grandstore.mapper.UserMapper;
import org.bpf.grandstore.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy
    ) {

        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(
                        userMapper::toDto
                )
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody UserDtoRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        User user = userMapper.toEntity(request);
        userRepository.save(user);

        UserDto userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long userId, @RequestBody UpdateUserRequest request) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(name = "id") Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> updateUser(
            @PathVariable(name = "id") Long userId,
            @RequestBody ChangePasswordRequest request
    ) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!Objects.equals(request.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

}

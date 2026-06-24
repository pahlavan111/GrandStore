package org.bpf.grandstore.controller;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.UserDto;
import org.bpf.grandstore.mapper.UserMapper;
import org.bpf.grandstore.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
//        var userDto = new UserDto(user.getId(), user.getName(), user.getLastName(), user.getEmail());
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

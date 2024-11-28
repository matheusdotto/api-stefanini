package tech.buildrun.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.springsecurity.annotations.PostListUser;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.model.dto.CreateUserDto;
import tech.buildrun.springsecurity.repository.UserRepository;
import tech.buildrun.springsecurity.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {

        userService.createUser(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PostListUser
    public ResponseEntity<List<User>> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}

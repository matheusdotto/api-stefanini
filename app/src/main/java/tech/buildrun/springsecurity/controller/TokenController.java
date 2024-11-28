package tech.buildrun.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.springsecurity.model.dto.LoginRequest;
import tech.buildrun.springsecurity.model.dto.LoginResponse;
import tech.buildrun.springsecurity.service.TokenService;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        var loginResponse = tokenService.authenticate(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}

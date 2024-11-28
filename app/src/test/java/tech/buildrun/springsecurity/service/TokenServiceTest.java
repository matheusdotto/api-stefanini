package tech.buildrun.springsecurity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import tech.buildrun.springsecurity.entities.Role;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.model.dto.LoginRequest;
import tech.buildrun.springsecurity.model.dto.LoginResponse;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private TokenService tokenService;

    @Test
    public void testAuthenticate() {

        LoginRequest loginRequest = new LoginRequest("user", "password");

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername("user");
        user.setPassword("encodedPassword");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        when(userRepository.findByUsername(loginRequest.username())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(true);

        when(jwtEncoder.encode(any())).thenReturn(mock(org.springframework.security.oauth2.jwt.Jwt.class));

        LoginResponse actualResponse = tokenService.authenticate(loginRequest);

        assertNotNull(actualResponse);

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
        verify(jwtEncoder, times(1)).encode(any());
    }

    @Test
    public void testAuthenticateUserNotFound() {

        LoginRequest loginRequest = new LoginRequest("nonexistentUser", "password");
        when(userRepository.findByUsername(loginRequest.username())).thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> tokenService.authenticate(loginRequest)
        );

        assertEquals("user or password is invalid!", exception.getMessage());
    }

    @Test
    public void testAuthenticateInvalidPassword() {

        LoginRequest loginRequest = new LoginRequest("testUser", "wrongPassword");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("correctPassword");

        when(userRepository.findByUsername(loginRequest.username())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(false);

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> tokenService.authenticate(loginRequest)
        );

        assertEquals("user or password is invalid!", exception.getMessage());
    }

    @Test
    public void testAuthenticateSuccess() {

        LoginRequest loginRequest = new LoginRequest("testUser", "password");

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        when(userRepository.findByUsername(loginRequest.username())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(true);

        var jwt = mock(org.springframework.security.oauth2.jwt.Jwt.class);
        when(jwt.getTokenValue()).thenReturn("mockToken");
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        LoginResponse response = tokenService.authenticate(loginRequest);

        assertNotNull(response);
        assertNotNull(response.accessToken());
        assertEquals(300L, response.expiresIn());

        verify(jwtEncoder, times(1)).encode(any());
    }
}

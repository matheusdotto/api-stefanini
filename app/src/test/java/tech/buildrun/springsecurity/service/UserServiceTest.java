package tech.buildrun.springsecurity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.springsecurity.entities.Role;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.model.dto.CreateUserDto;
import tech.buildrun.springsecurity.repository.RoleRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUserUserAlreadyExists() {

        CreateUserDto dto = new CreateUserDto("existingUser", "password");

        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.of(new User()));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.createUser(dto)
        );

        assertEquals("Username already exists", exception.getReason());

        verify(userRepository, times(1)).findByUsername(dto.username());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUserSuccess() {

        CreateUserDto dto = new CreateUserDto("newUser", "password");

        Role basicRole = new Role();
        basicRole.setRoleId(1L);
        basicRole.setName("BASIC");

        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.empty());
        when(roleRepository.findByName("BASIC")).thenReturn(basicRole);
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");

        CreateUserDto createdUser = userService.createUser(dto);

        assertNotNull(createdUser);
        assertEquals(dto.username(), createdUser.username());
        assertEquals(dto.password(), createdUser.password());

        verify(userRepository, times(1)).findByUsername(dto.username());
        verify(roleRepository, times(1)).findByName("BASIC");
        verify(userRepository, times(1)).save(any(User.class));
    }
}

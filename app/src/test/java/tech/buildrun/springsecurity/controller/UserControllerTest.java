package tech.buildrun.springsecurity.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.model.dto.CreateUserDto;
import tech.buildrun.springsecurity.repository.UserRepository;
import tech.buildrun.springsecurity.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testNewUser() {

        CreateUserDto createUserDto = new CreateUserDto("username", "password");

        ResponseEntity<Void> response = userController.newUser(createUserDto);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).createUser(createUserDto);
    }


    @Test
    public void testListUsers() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        ResponseEntity<List<User>> response = userController.listUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(userRepository, times(1)).findAll();
    }
}

package tech.buildrun.springsecurity.entities;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.buildrun.springsecurity.model.dto.LoginRequest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {

    @Test
    public void testUserSettersAndGetters() {

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password");

        var id = user.getUserId();

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleId(1L);
        role.setName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);

        assertEquals(id, user.getUserId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testUserEqualsAndHashCode() {

        User user1 = new User();
        user1.setUserId(UUID.randomUUID());
        user1.setUsername("testUser");
        user1.setPassword("password");

        User user2 = new User();
        user2.setUserId(user1.getUserId());
        user2.setUsername("testUser");
        user2.setPassword("password");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testIsLoginCorrectValidPassword() {

        User user = new User();
        user.setPassword("encodedPassword");

        LoginRequest loginRequest = new LoginRequest("testUser", "rawPassword");

        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(true);

        boolean result = user.isLoginCorrect(loginRequest, passwordEncoder);

        assertTrue(result);
        verify(passwordEncoder, times(1)).matches(loginRequest.password(), user.getPassword());
    }

    @Test
    public void testIsLoginCorrectInvalidPassword() {

        User user = new User();
        user.setPassword("encodedPassword");

        LoginRequest loginRequest = new LoginRequest("testUser", "wrongPassword");

        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(false);

        boolean result = user.isLoginCorrect(loginRequest, passwordEncoder);

        assertFalse(result);
        verify(passwordEncoder, times(1)).matches(loginRequest.password(), user.getPassword());
    }
}

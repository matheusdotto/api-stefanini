package tech.buildrun.springsecurity.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.buildrun.springsecurity.config.AdminUserConfig;
import tech.buildrun.springsecurity.entities.Role;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.repository.RoleRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AdminUserTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminUserConfig adminUserConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdminUserExists() {

        Role adminRole = new Role();
        adminRole.setRoleId(1L);
        adminRole.setName("ADMIN");

        User existingAdmin = new User();
        existingAdmin.setUsername("admin");

        when(roleRepository.findByName("ADMIN")).thenReturn(adminRole);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(existingAdmin));

        adminUserConfig.run();

        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(userRepository, times(1)).findByUsername("admin");
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    public void testRunWhenAdminAlreadyExists() {

        var adminUser = new User();
        adminUser.setUsername("admin");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));

        adminUserConfig.run();

        verify(userRepository, times(1)).findByUsername("admin");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRunWhenAdminDoesNotExist() {

        var roleAdmin = new Role();
        roleAdmin.setName("ADMIN");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ADMIN")).thenReturn(roleAdmin);
        when(passwordEncoder.encode("123")).thenReturn("encodedPassword");

        adminUserConfig.run();

        verify(userRepository, times(1)).findByUsername("admin");
        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(passwordEncoder, times(1)).encode("123");
        verify(userRepository, times(1)).save(any(User.class));
    }
}

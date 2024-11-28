package tech.buildrun.springsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.enums.Values;
import tech.buildrun.springsecurity.model.dto.CreateUserDto;
import tech.buildrun.springsecurity.monitorable.Monitorable;
import tech.buildrun.springsecurity.repository.RoleRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Monitorable("create.user.method")
    public CreateUserDto createUser(CreateUserDto dto) {
        var basicRole = roleRepository.findByName(Values.BASIC.name());

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exists");
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);
        return dto;
    }
}

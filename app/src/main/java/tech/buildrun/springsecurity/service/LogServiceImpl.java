package tech.buildrun.springsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.buildrun.springsecurity.entities.Log;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.repository.LogRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Override
    public List<Log> getLogsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return logRepository.findLogsByUserId(user.getUserId());
    }

    @Override
    public List<Log> getLogsByCep(String cep) {
        return logRepository.findByCep(cep);
    }
}

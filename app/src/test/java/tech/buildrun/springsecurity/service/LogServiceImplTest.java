package tech.buildrun.springsecurity.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.buildrun.springsecurity.entities.Log;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.repository.LogRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LogServiceImpl logService;

    public LogServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLogsByUser_Success() {

        String username = "testUser";
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername(username);

        Log log1 = new Log();
        log1.setId(1L);
        log1.setUsername(username);

        Log log2 = new Log();
        log2.setId(2L);
        log2.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(logRepository.findLogsByUserId(user.getUserId())).thenReturn(Arrays.asList(log1, log2));

        List<Log> logs = logService.getLogsByUser(username);

        assertNotNull(logs);
        assertEquals(2, logs.size());
        verify(userRepository, times(1)).findByUsername(username);
        verify(logRepository, times(1)).findLogsByUserId(user.getUserId());
    }

    @Test
    void testGetLogsByUser_UserNotFound() {

        String username = "unknownUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> logService.getLogsByUser(username));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verify(logRepository, never()).findLogsByUserId(any());
    }

    @Test
    void testGetLogsByCep_Success() {

        String cep = "12345-678";
        Log log1 = new Log();
        log1.setId(1L);
        log1.setCep(cep);

        Log log2 = new Log();
        log2.setId(2L);
        log2.setCep(cep);

        when(logRepository.findByCep(cep)).thenReturn(Arrays.asList(log1, log2));

        List<Log> logs = logService.getLogsByCep(cep);

        assertNotNull(logs);
        assertEquals(2, logs.size());
        verify(logRepository, times(1)).findByCep(cep);
    }

    @Test
    void testGetLogsByCep_NoLogsFound() {

        String cep = "00000-000";
        when(logRepository.findByCep(cep)).thenReturn(Arrays.asList());

        List<Log> logs = logService.getLogsByCep(cep);

        assertNotNull(logs);
        assertTrue(logs.isEmpty());
        verify(logRepository, times(1)).findByCep(cep);
    }
}
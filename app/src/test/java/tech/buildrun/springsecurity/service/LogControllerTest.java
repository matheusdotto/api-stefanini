package tech.buildrun.springsecurity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tech.buildrun.springsecurity.controller.LogController;
import tech.buildrun.springsecurity.entities.Log;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogControllerTest {

    @Mock
    private LogService logService;

    @InjectMocks
    private LogController logController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLogsByUsername_Success() {
        String username = "testUser";
        Log log1 = new Log();
        log1.setId(1L);
        log1.setUsername(username);

        Log log2 = new Log();
        log2.setId(2L);
        log2.setUsername(username);

        when(logService.getLogsByUser(username)).thenReturn(Arrays.asList(log1, log2));

        ResponseEntity<List<Log>> response = logController.getLogsByUsername(username);

        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(logService, times(1)).getLogsByUser(username);
    }

    @Test
    void testGetLogsByUsername_NoLogs() {

        String username = "unknownUser";
        when(logService.getLogsByUser(username)).thenReturn(Arrays.asList());

        ResponseEntity<List<Log>> response = logController.getLogsByUsername(username);

        assertEquals(0, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(logService, times(1)).getLogsByUser(username);
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

        when(logService.getLogsByCep(cep)).thenReturn(Arrays.asList(log1, log2));

        List<Log> response = logController.getLogsByCep(cep);

        assertEquals(2, response.size());
        verify(logService, times(1)).getLogsByCep(cep);
    }

    @Test
    void testGetLogsByCep_NoLogs() {

        String cep = "00000-000";
        when(logService.getLogsByCep(cep)).thenReturn(Arrays.asList());

        List<Log> response = logController.getLogsByCep(cep);

        assertEquals(0, response.size());
        verify(logService, times(1)).getLogsByCep(cep);
    }
}

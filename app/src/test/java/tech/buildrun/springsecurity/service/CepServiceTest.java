package tech.buildrun.springsecurity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import tech.buildrun.springsecurity.entities.Log;
import tech.buildrun.springsecurity.entities.User;
import tech.buildrun.springsecurity.repository.LogRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CepServiceTest {


    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private CepServiceImpl cepService;

    @Test
    public void testGetCepWithNullCep() {
        HttpHeaders headers = new HttpHeaders();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cepService.getCep(null, headers, "testUser")
        );

        assertEquals("CEP não pode ser nulo ou vazio", exception.getMessage());
    }


    @Test
    public void testGetCepWithValidCep() {
        String cep = "12345678";
        HttpHeaders headers = new HttpHeaders();
        headers.add("GTW-ID-HEADER", "test-id");

        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        ReflectionTestUtils.setField(cepService, "uriCep", cep);

        Map<String, String> mockResponse = Map.of(
                "logradouro", "Rua Teste",
                "bairro", "Bairro Teste",
                "cidade", "Cidade Teste",
                "estado", "Estado Teste"
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        String result = cepService.getCep(cep, headers, username);

        assertNotNull(result);
        assertTrue(result.contains("Rua Teste"));
        assertTrue(result.contains("Bairro Teste"));
        assertTrue(result.contains("Cidade Teste"));
        assertTrue(result.contains("Estado Teste"));

        verify(logRepository, times(1)).save(any(Log.class));
    }
}
package tech.buildrun.springsecurity.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import tech.buildrun.springsecurity.service.CepServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CepControllerTest {

    @Mock
    private CepServiceImpl cepService;

    @InjectMocks
    private CepController cepController;

    @Test
    public void testConsultaCep() {
        String cep = "50010000";
        String username = "testUser";
        HttpHeaders headers = new HttpHeaders();
        String expectedResponse = "Sample Response";

        when(cepService.getCep(cep, headers, username)).thenReturn(expectedResponse);

        String actualResponse = cepController.consultaCep(cep, headers, username);

        assertEquals(expectedResponse, actualResponse);
        verify(cepService, times(1)).getCep(cep, headers, username);
    }

    @Test
    public void testCadastrarCep() {
        String cep = "50010000";
        String expectedResponse = "CEP cadastrado com sucesso: " + cep;

        String actualResponse = cepController.cadastrarCep(cep);

        assertEquals(expectedResponse, actualResponse);
    }
}
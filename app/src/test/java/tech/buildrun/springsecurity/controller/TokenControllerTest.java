
package tech.buildrun.springsecurity.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import tech.buildrun.springsecurity.model.dto.LoginRequest;
import tech.buildrun.springsecurity.model.dto.LoginResponse;
import tech.buildrun.springsecurity.service.TokenService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TokenControllerTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenController tokenController;

    @Test
    public void testLogin() {

        LoginRequest loginRequest = new LoginRequest("user", "password");
        LoginResponse expectedResponse = new LoginResponse("mockToken", 100L);
        when(tokenService.authenticate(loginRequest)).thenReturn(expectedResponse);

        ResponseEntity<LoginResponse> response = tokenController.login(loginRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mockToken", response.getBody().accessToken());

        verify(tokenService, times(1)).authenticate(loginRequest);
    }
}

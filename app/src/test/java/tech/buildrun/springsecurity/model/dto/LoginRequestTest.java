package tech.buildrun.springsecurity.model.dto;

import org.junit.jupiter.api.Test;
import tech.buildrun.springsecurity.service.TokenService;

import static org.mockito.Mockito.*;

public class LoginRequestTest {

    @Test
    public void testDtoUsageInService() {

        LoginRequest loginRequest = new LoginRequest("mockUser", "mockPass");
        TokenService mockService = mock(TokenService.class);

        mockService.authenticate(loginRequest);

        verify(mockService, times(1)).authenticate(loginRequest);
    }
}

package tech.buildrun.springsecurity.model.dto;

import org.junit.jupiter.api.Test;
import tech.buildrun.springsecurity.service.UserService;

import static org.mockito.Mockito.*;

public class CreateUserDtoTest {

    @Test
    public void testDtoUsageInService() {

        CreateUserDto dto = new CreateUserDto("mockUsername", "mockPassword");
        UserService mockService = mock(UserService.class);

        mockService.createUser(dto);

        verify(mockService, times(1)).createUser(dto);
    }

}

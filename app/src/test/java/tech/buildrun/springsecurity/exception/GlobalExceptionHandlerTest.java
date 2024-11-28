package tech.buildrun.springsecurity.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleUserNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UserNotFoundException exception = new UserNotFoundException("User not found");

        String response = handler.handleUserNotFoundException(exception);

        assertEquals("User not found", response);
    }

    @Test
    public void testHandleInvalidInputException() {

        String errorMessage = "Invalid input provided";
        InvalidInputException exception = new InvalidInputException(errorMessage);

        String response = exceptionHandler.handleInvalidInputException(exception);

        assertNotNull(response);
        assertEquals(errorMessage, response);
    }
}

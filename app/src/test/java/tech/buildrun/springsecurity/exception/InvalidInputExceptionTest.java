package tech.buildrun.springsecurity.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidInputExceptionTest {

    @Test
    public void testExceptionMessage() {
        InvalidInputException exception = new InvalidInputException("Invalid input data");
        assertEquals("Invalid input data", exception.getMessage());
    }
}

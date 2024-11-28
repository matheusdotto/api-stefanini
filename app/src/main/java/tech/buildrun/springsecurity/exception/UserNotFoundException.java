package tech.buildrun.springsecurity.exception;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String message) {
        super(message);
    }
}

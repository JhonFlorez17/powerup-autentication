package co.com.powerup.model.users.exception;

public class UserValidationException extends  RuntimeException  {
    public UserValidationException(String message) {
        super(message);
    }
}

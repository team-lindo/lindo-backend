package team.lindo.backend.application.common.exception;

public class UserAlreadyExistsException extends BusinessException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

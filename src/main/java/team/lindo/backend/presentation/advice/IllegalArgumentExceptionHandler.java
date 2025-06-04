package team.lindo.backend.presentation.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

@RestControllerAdvice
@Order(3)
public class IllegalArgumentExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureResponse.Response handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseGenerator.getBusinessErrorResponse("E101", exception.getMessage());
    }
}

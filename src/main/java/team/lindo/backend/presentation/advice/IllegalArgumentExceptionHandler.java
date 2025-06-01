package team.lindo.backend.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

public class IllegalArgumentExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureResponse.Response handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseGenerator.getBusinessErrorResponse("E101", exception.getMessage());
    }
}

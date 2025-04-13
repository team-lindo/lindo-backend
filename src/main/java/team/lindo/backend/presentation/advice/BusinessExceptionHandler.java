package team.lindo.backend.presentation.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.lindo.backend.application.common.exception.BusinessException;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

@RestControllerAdvice
@Order(3)
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureResponse.Response handleBusinessException(BusinessException exception) {
        return ResponseGenerator.getBusinessErrorResponse("E000", exception.getMessage());
    }
}

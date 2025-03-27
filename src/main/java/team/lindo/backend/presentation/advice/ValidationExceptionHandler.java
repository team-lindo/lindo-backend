package team.lindo.backend.presentation.advice;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(1)
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)  // 메서드 매개변수 유효성 검증 실패 (제약 조건 위반?)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureResponse.Response handleConstraintViolationException(ConstraintViolationException exception) {
        List<FailureResponse.FieldError> fieldErrors = exception.getConstraintViolations().stream()
                .map(violation -> {
                    String invalidValue = "null";
                    if(violation.getInvalidValue() != null) {
                        invalidValue = violation.getInvalidValue().toString();
                    }

                    return FailureResponse.FieldError.of(violation.getPropertyPath().toString()
                            , invalidValue
                            , violation.getMessage());
                })
                .collect(Collectors.toList());

        return ResponseGenerator.getValidationErrorResponse(fieldErrors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)  // 객체 유효성 검사 실패
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureResponse.Response handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseGenerator.getValidationErrorResponse(exception.getBindingResult());
    }
}

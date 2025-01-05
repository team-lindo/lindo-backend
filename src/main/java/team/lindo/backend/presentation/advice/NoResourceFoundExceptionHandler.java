package team.lindo.backend.presentation.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

@RestControllerAdvice
@Order(2)  // 숫자 낮을수록 우선순위 높음. 더 낮은 우선순위(1)의 핸들러 있을 경우 해당 핸들러 아후 실행.
public class NoResourceFoundExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)  // 요청 주소가 잘못된 경우 Spring Security가 뱉는 예외
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FailureResponse.Response handleNoResourceFoundException(NoResourceFoundException exception) {
        exception.printStackTrace();
        return ResponseGenerator.getUnknownErrorResponse();
    }
}

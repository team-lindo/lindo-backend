package team.lindo.backend.presentation.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

@RestControllerAdvice  // 전역적으로 발생하는 예외들 처리 (모든 Controller에서 발생하는 예외 한 곳에서 처리)
@Order(Ordered.LOWEST_PRECEDENCE)  // 가장 낮은 우선순위 -> 다른 @RestControllerAdvice에서 처리하지 못한 예외들 처리
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 반환되는 HTTP 응답 상태의 코드
    public FailureResponse.Response handleGlobalException(Exception exception) {
        exception.printStackTrace();
        return ResponseGenerator.getUnknownErrorResponse();
    }
}

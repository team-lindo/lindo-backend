package team.lindo.backend.presentation.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;

@RestControllerAdvice
@Order(4)
@Slf4j
public class AuthenticationExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
    public FailureResponse.Response handleBadCredentialsException(BadCredentialsException exception) {
        log.warn("인증 실패: {}", exception.getMessage());
        return ResponseGenerator.getAuthenticationFailureResponse();
    }

    @ExceptionHandler(AuthenticationException.class) // 인증 관련 기타 예외 처리
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
    public FailureResponse.Response handleAuthenticationException(AuthenticationException exception) {
        log.warn("인증 실패 (기타 예외): {}", exception.getMessage());
        return ResponseGenerator.getAuthenticationFailureResponse();
    }
}

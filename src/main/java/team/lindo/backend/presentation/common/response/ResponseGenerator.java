package team.lindo.backend.presentation.common.response;

import org.springframework.validation.BindingResult;
import team.lindo.backend.presentation.common.response.payload.FailureResponse;
import team.lindo.backend.presentation.common.response.payload.SuccessResponse;

import java.util.List;

public class ResponseGenerator {
    private ResponseGenerator() {
        // utility class -> instantiation X
    }

    public static SuccessResponse.Single<String> getSuccessResponse() {
        return new SuccessResponse.Single<>("OK");
    }

    public static <T> SuccessResponse.Single<T> getSingleDataResponse(T item) {
        return new SuccessResponse.Single<>(item);
    }

    public static <T> SuccessResponse.Multiple<T> getMultipleDataResponse(List<T> items) {
        return new SuccessResponse.Multiple<>(items);
    }

    public static <T> SuccessResponse.Paginated<T> getPaginatedDataResponse(long totalElements
            , int page, List<T> items) {
        int totalPages = (int) Math.ceil((double) totalElements / items.size());

        return new SuccessResponse.Paginated<>(totalPages, totalElements, page, items.size(), items);
    }

    public static FailureResponse.Response getUnknownErrorResponse() {
        return FailureResponse.Response.withUnknownError();
    }

    public static FailureResponse.Response getBusinessErrorResponse(String errorCode, String errorMessage) {
        return FailureResponse.Response.withBusinessError(errorCode, errorMessage);
    }

    public static FailureResponse.Response getValidationErrorResponse(List<FailureResponse.FieldError> fieldErrors) {
        return FailureResponse.Response.withValidationError(fieldErrors);
    }

    public static FailureResponse.Response getValidationErrorResponse(BindingResult bindingResult) {
        return FailureResponse.Response.withValidationError(bindingResult);
    }

    public static FailureResponse.Response getAuthenticationFailureResponse() {
        return FailureResponse.Response.withBusinessError("AUTH_INVALID_CREDENTIALS", "아이디 또는 비밀번호가 잘못되었습니다.");
    }

    public static FailureResponse.Response getAuthorizationFailureResponse() {
        return FailureResponse.Response.withBusinessError("AUTH_FORBIDDEN", "접근 권한이 없습니다.");
    }

    public static FailureResponse.Response getSecurityFailureResponse() {
        return FailureResponse.Response.withBusinessError("SECURITY_ERROR", "보안 관련 오류가 발생했습니다.");
    }
}

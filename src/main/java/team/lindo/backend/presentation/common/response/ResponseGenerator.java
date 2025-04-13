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
}

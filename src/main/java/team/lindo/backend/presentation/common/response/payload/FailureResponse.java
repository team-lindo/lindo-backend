package team.lindo.backend.presentation.common.response.payload;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import team.lindo.backend.presentation.common.response.ResponseCode;

import java.util.List;
import java.util.stream.Collectors;

public class FailureResponse {
    @Getter
    public static class Response {
        private final String errorCode;
        private final String errorMessage;
        private final ResponseCode result;

        private static final String VALIDATION_ERROR_CODE = "FLD000400";
        private static final String UNKNOWN_ERROR_CODE = "NAK000500";
        private static final String UNKNOWN_ERROR_MESSAGE = "unknown";

        public Response(String errorCode, String errorMessage) {
            this(errorCode, errorMessage, ResponseCode.FAILURE);
        }

        public Response(String errorCode, String errorMessage, ResponseCode result) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.result = result;
        }

        public static Response withUnknownError() {
            return new Response(UNKNOWN_ERROR_CODE, UNKNOWN_ERROR_MESSAGE);
        }

        public static Response withBusinessError(String errorCode, String errorMessage) {
            return new Response(errorCode, errorMessage);
        }

        public static Response withValidationError(FieldError fieldError) {
            return new Response(VALIDATION_ERROR_CODE, fieldError.getMessage());
        }

        public static Response withValidationError(List<FieldError> fieldErrors) {
            return new Response(VALIDATION_ERROR_CODE, fieldErrors.get(0).getMessage());
        }

        public static Response withValidationError(BindingResult bindingResult) {
            String message = FieldError.of(bindingResult).get(0).getMessage();
            return new Response(VALIDATION_ERROR_CODE, message);
        }
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String message;

        private FieldError(String field, String value, String message) {
            this.field = field;
            this.value = value;
            this.message = message;
        }

        public static FieldError of(String field, String value, String message) {
            return new FieldError(field, value, message);
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> of(error.getField(), error.getRejectedValue().toString()
                            , error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}

package team.lindo.backend.presentation.common.response.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {  // 프롱트엔드와 연결하기 위해 추가한 에러응답 명세
    private String message;
    private int statusCode;

    public static ErrorResponseDto from(FailureResponse.Response response, int httpStatusCode) {
        return new ErrorResponseDto(response.getErrorMessage(), httpStatusCode);
    }
}

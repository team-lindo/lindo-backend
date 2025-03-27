package team.lindo.backend.presentation.common.response;

public enum ResponseCode {
    FAILURE(1000),
    SUCCESS(2000);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }
}

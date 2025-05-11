package team.lindo.backend.application.user.dto;

import lombok.Getter;

@Getter
public class LogoutResponseDto {
    private String message;

    public LogoutResponseDto(String message) {
        this.message = message;
    }
}

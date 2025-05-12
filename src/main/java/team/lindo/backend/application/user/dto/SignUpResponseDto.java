package team.lindo.backend.application.user.dto;

import lombok.Getter;

@Getter
public class SignUpResponseDto {
    Long id;
    String nickname;

    public SignUpResponseDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}

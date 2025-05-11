package team.lindo.backend.application.social.dto;

import lombok.Getter;

@Getter
public class UnfollowResponseDto {
    Long id;

    public UnfollowResponseDto(Long id) {
        this.id = id;
    }
}

package team.lindo.backend.application.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdatePostingRequestDto {
    private String postId;   // string으로 받기 원한다면
    private String content;
}

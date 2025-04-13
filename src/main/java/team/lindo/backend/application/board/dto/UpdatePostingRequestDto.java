package team.lindo.backend.application.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdatePostingRequestDto {
    private String title;
    private String content;
    private String imageUrl;
    private List<Long> productIds;
}

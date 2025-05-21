package team.lindo.backend.application.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdatePostingRequestDto {
    private String content;
    private List<String> imageUrls;
    private List<Long> productIds;
}

package team.lindo.backend.application.board.dto;

import lombok.Getter;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.time.LocalDateTime;

@Getter
public class PostingResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private UserSummaryDto user; // 작성자 정보 포함

    public PostingResponseDto(Posting posting) {
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.imageUrl = posting.getImageUrl();
        this.createdAt = posting.getCreatedAt();
        this.user = new UserSummaryDto(posting.getUser());
    }
}

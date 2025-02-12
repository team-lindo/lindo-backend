package team.lindo.backend.application.board.dto;

import lombok.Getter;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.time.LocalDateTime;

@Getter
public class PostingSummaryDto {
    private Long id;
    private String title;
    private String content;  //! 해당 필드의 의미? "content": "첫 번째 게시물" 이라고 했던데 정확한 의미? entity에선 본문 저장 필드
    private String imageUrl;
    private LocalDateTime createdAt;
    private UserSummaryDto user; // 작성자 정보 포함

    public PostingSummaryDto(Posting posting) {
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.imageUrl = posting.getImageUrl();
        this.createdAt = posting.getCreatedAt();
        this.user = new UserSummaryDto(posting.getUser());
    }
}

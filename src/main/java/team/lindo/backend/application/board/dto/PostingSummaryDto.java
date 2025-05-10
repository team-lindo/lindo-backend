package team.lindo.backend.application.board.dto;

import lombok.Getter;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostingSummaryDto {
    private Long id;
    private String title;
    private String content;
    private List<String> imageUrls;
    private UserSummaryDto user; // 작성자 정보

    public PostingSummaryDto(Posting posting) {
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.imageUrls = posting.getImageUrls();
        this.user = new UserSummaryDto(posting.getUser());
    }
}

package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.lindo.backend.application.board.entity.Posting;

@Getter
@AllArgsConstructor
public class UpdatePostResponseDto {
    private String postId;
    private String content;
    private String updatedAt;

    public UpdatePostResponseDto(Posting posting) {
        this.postId = posting.getId().toString();
        this.content = posting.getContent();
        this.updatedAt = posting.getUpdatedAt().toString();
    }

}

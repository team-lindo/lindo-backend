package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.board.entity.Comment;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.dto.UserSummaryDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private String commentId;
    private String commentContent;
    private String createdAt;
    private UserSummaryDto user;

    public CommentDto(Comment comment) {
        this.commentId = comment.getId().toString();
        this.commentContent = comment.getContent();
        this.createdAt = comment.getCreatedAt().toString();
        this.user = new UserSummaryDto(comment.getUser());
    }
}

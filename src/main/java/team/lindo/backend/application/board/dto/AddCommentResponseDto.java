package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCommentResponseDto {
    private String postId;
    private CommentDto comment;
}

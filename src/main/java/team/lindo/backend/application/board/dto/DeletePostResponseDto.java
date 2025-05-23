package team.lindo.backend.application.board.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class DeletePostResponseDto {
    private String postId;

    public DeletePostResponseDto(String postId) {
        this.postId = postId;
    }
}

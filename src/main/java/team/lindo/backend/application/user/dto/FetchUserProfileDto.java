package team.lindo.backend.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.board.dto.PostDto;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchUserProfileDto {
    private Long id;
    private String nickname;
    private Long postCount;
    private List<PostDto> posts;
}

package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPageResponseDto {

    private List<PostDto> posts;
    private boolean hasNext; // 다음 페이지가 있는지
}

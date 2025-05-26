package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadPostResponseDto {
    private Long id;
    private UserSummaryDto user;
    private String content;
    private List<String> images;
    private List<CommentDto> comments;  // 선택 사항
    private List<TaggedProductGroupDto> taggedProducts;
}

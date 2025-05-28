package team.lindo.backend.application.board.dto;

import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class CreatePostingRequestDto {
    private String content;
    private List<String> imageUrls;
    private List<TaggedProductGroupDto> taggedProducts;
    private Set<String> hashtags;
}

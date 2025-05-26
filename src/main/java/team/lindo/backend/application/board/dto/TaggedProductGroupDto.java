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
public class TaggedProductGroupDto {
    private String imageId;
    private List<TaggedProductDto> tags;
}

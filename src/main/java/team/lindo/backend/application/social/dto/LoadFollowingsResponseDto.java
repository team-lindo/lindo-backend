package team.lindo.backend.application.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadFollowingsResponseDto {
    private List<FollowingDto> users;
    private Long totalCount;
}

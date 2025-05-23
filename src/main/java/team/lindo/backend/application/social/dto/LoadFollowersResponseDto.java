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
public class LoadFollowersResponseDto {
    private List<FollowerDto> users;
    private Long totalCount;
    }

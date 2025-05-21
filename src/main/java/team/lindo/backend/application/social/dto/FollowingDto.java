package team.lindo.backend.application.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.lindo.backend.application.user.dto.UserSummaryDto;
import team.lindo.backend.application.user.entity.User;


@Getter
@AllArgsConstructor
public class FollowingDto {
    private Long followingId;
    private String followingNickname;
}

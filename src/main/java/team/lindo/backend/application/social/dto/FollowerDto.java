package team.lindo.backend.application.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.user.dto.UserSummaryDto;
import team.lindo.backend.application.user.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerDto {
    private Long followerId;
    private String followerNickname;

}

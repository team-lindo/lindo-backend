package team.lindo.backend.application.user.dto;

import lombok.Getter;
import team.lindo.backend.application.user.entity.User;

@Getter
public class UserSummaryDto {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}

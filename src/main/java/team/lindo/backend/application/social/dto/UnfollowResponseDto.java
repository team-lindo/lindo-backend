package team.lindo.backend.application.social.dto;

import lombok.Getter;

@Getter
public class UnfollowResponseDto {
    private Long  unfollowedUserId;
    private Long followingsCount;
    private Long followersCount;


    public UnfollowResponseDto(Long unfollowedUserId, Long followingsCount, Long followersCount) {
        this.unfollowedUserId = unfollowedUserId;
        this.followingsCount = followingsCount;
        this.followersCount = followersCount;
    }
}

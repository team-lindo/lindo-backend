package team.lindo.backend.application.social.dto;

public class FollowResponseDto {
    private Long id; // 임의로 만든거라 고쳐야함 명세서보고
    private FollowingDto followedUser;
    private Long followingsCount;
    private Long followersCount;

    public FollowResponseDto(Long id, FollowingDto followedUser, Long followingsCount, Long followersCount) {
        this.id = id;
        this.followedUser = followedUser;
        this.followingsCount = followingsCount;
        this.followersCount = followersCount;
    }

}

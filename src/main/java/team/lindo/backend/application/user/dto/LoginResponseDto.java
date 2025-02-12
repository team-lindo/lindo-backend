package team.lindo.backend.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.board.dto.PostingSummaryDto;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.social.entity.Follow;
import team.lindo.backend.application.user.entity.User;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private Long id;

    private String nickname;

    private String email;

    private List<PostingSummaryDto> posts;

    private List<UserSummaryDto> followings;

    private List<UserSummaryDto> followers;

    public LoginResponseDto(User user, List<Posting> posts, List<Follow> followings, List<Follow> followers) {
        id = user.getId();
        nickname = user.getNickname();
        email = user.getEmail();
        this.posts = posts.stream().map(PostingSummaryDto::new).toList();
        this.followings = followings.stream().map(f -> new UserSummaryDto(f.getFollowing())).toList();
        this.followers = followers.stream().map(f -> new UserSummaryDto(f.getFollower())).toList();
    }
}

package team.lindo.backend.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDto {  // UserDto?
    private Long id;
    private String nickname;
    private String email;

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}

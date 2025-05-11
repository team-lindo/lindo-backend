package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.social.dto.UnfollowResponseDto;
import team.lindo.backend.application.social.service.FollowService;
import team.lindo.backend.application.user.dto.UserSummaryDto;
import team.lindo.backend.application.user.service.UserService;
import team.lindo.backend.common.util.SecurityUtil;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    @PatchMapping("/{id}")
    public ResponseEntity<UserSummaryDto> follow(@PathVariable Long id) {
        Long followerId = SecurityUtil.getCurrentUserId();  // 구현 필요
        followService.followUser(followerId, id);

        return ResponseEntity.ok(userService.loadUserInfo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UnfollowResponseDto> unfollow(@PathVariable Long id) {
        Long followerId = SecurityUtil.getCurrentUserId();
        followService.unfollowUser(followerId, id);

        return ResponseEntity.ok(new UnfollowResponseDto(id));
    }
}

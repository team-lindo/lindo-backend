package team.lindo.backend.presentation.controller.app;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.social.dto.LoadFollowersResponseDto;
import team.lindo.backend.application.social.dto.LoadFollowingsResponseDto;
import team.lindo.backend.application.social.service.FollowService;
import team.lindo.backend.application.user.dto.*;
import team.lindo.backend.application.user.service.UserService;
import team.lindo.backend.util.SecurityUtil;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app/users")  //! 원래 "/api/v1/app/users" 였는데 이런 주소로 한 이유 있는지 형한테 물어보기
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FollowService followService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> registerUser(@Valid @RequestBody SignUpRequestDto request) {
        UserSummaryDto registeredUserInfo = userService.registerUser(request);
        return ResponseEntity.ok(new SignUpResponseDto(registeredUserInfo.getId(), registeredUserInfo.getNickname()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")  // GET으로 하면 그냥 url 입력했는데 로그아웃될 수 있으니 위험 + 로그아웃 -> 서버의 상태 변경하는 행동
    public ResponseEntity<LogoutResponseDto> logout() {
        return ResponseEntity.ok(userService.logout());
    }


    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> loadMyInfo() {
        long myId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.loadMyUserInfo(myId));
    }

    @GetMapping("/me/followings")
    public ResponseEntity<LoadFollowingsResponseDto> getFollowings() {
        Long myId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(followService.loadFollowings(myId));
    }

    @GetMapping("/me/followers")
    public ResponseEntity<LoadFollowersResponseDto> getFollowers() {
        Long myId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(followService.loadFollowers(myId));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<FetchUserProfileDto> fetchUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.loadUserInfo(id));
    }
}

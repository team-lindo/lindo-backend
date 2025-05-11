package team.lindo.backend.presentation.controller.app;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.user.dto.*;
import team.lindo.backend.application.user.service.UserService;
import team.lindo.backend.common.util.SecurityUtil;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequestDto request) {
        userService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")  // GET으로 하면 그냥 url 입력했는데 로그아웃될 수 있으니 위험 + 로그아웃 -> 서버의 상태 변경하는 행동
    public ResponseEntity<LogoutResponseDto> logout() {
        userService.logout();
        return ResponseEntity.ok(new LogoutResponseDto("Logged out successfully"));
    }

    @GetMapping("/load")
    public ResponseEntity<UserSummaryDto> loadMyInfo() {
        long myId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.loadUserInfo(myId));
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<UserSummaryDto> loadUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.loadUserInfo(id));
    }
}

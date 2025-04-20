package team.lindo.backend.presentation.controller.app;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.user.dto.LoginRequestDto;
import team.lindo.backend.application.user.dto.LoginResponseDto;
import team.lindo.backend.application.user.dto.SignUpRequestDto;
import team.lindo.backend.application.user.service.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")  //! 원래 "/api/v1/app/users" 였는데 이런 주소로 한 이유 있는지 형한테 물어보기
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
    public ResponseEntity<Map<String, String>> logout() {
        userService.logout();
        return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
    }
}

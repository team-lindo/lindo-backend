package team.lindo.backend.presentation.controller.app;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.user.dto.SignUpRequestDto;
import team.lindo.backend.application.user.service.UserService;

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
}

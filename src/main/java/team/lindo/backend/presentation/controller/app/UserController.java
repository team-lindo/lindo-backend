package team.lindo.backend.presentation.controller.app;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.application.user.service.UserService;

@RestController
@RequestMapping("/api/v1/app/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}

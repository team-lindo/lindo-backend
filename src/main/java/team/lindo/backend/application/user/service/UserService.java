package team.lindo.backend.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public String example() {
        return "Hi! user!";
    }
}

package team.lindo.backend.application.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExampleService {
    @Transactional
    public String example() {
        return "Example";
    }
}

package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.application.example.service.ExampleService;

@RestController
@RequestMapping("/api/v1/app/example")
@RequiredArgsConstructor
public class ExampleAppController {
    private final ExampleService exampleService;

    @GetMapping("")
    public String example() {
        return exampleService.example();
    }
}

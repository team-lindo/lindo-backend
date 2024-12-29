package team.lindo.backend.presentation.controller.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app/example")
public class ExampleAppController {
    @GetMapping("")
    public String example() {
        return "Example";
    }
}

package team.lindo.backend.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.presentation.common.response.ResponseGenerator;
import team.lindo.backend.presentation.common.response.payload.SuccessResponse;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {
    @GetMapping("")
    public SuccessResponse.Single<String> healthCheck() {
        return ResponseGenerator.getSuccessResponse();
    }
}

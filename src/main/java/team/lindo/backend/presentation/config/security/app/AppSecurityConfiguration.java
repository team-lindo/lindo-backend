package team.lindo.backend.presentation.config.security.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import team.lindo.backend.presentation.config.security.SecurityConfigurationContract;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

// 일반 앱 접속 고객들에 대한 보안 정책
@Configuration
public class AppSecurityConfiguration extends SecurityConfigurationContract {
    public AppSecurityConfiguration(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    // TODO 인증 필터 생성 후 3A (Authentication, Authorization, Access-Control) 구현
    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        return configureNeedsToBeAuthenticatedSecuritySettings(http)
                .securityMatchers(matchers -> matchers.requestMatchers(getRequestMatchers()))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .build();
    }

    // api/v1/app/** 엔드포인트에 대한 보안 정책
    @Override
    protected RequestMatcher[] getRequestMatchers() {
        return new RequestMatcher[] { antMatcher("/api/v1/app/**") };
    }
}

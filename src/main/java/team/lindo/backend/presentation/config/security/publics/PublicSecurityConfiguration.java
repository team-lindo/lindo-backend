package team.lindo.backend.presentation.config.security.publics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import team.lindo.backend.presentation.config.security.SecurityConfigurationContract;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class PublicSecurityConfiguration extends SecurityConfigurationContract {
    public PublicSecurityConfiguration(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    // 모두에게 허용하는 보안 정책(테스트에서 로그인 인증 없이 열기 위해 주석 처리)
    /*@Bean
    @Order(1)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        return this.configureCommonSecuritySettings(http) // 보안 공통 설정
                .securityMatchers(matchers -> matchers.requestMatchers(getRequestMatchers())) // health check endpoint에 대해서
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()) // 모두에게 접근 허용
                .build();
    }*/

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 요청 허용
                );

        return http.build();
    }

    // 앱에서 관리하지 않는 엔드포인트에 대한 보안 정책
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SecurityFilterChain notManagedSecurityFilterChain(HttpSecurity http) throws Exception {
        return this.configureCommonSecuritySettings(http) // 보안 공통 설정
                .securityMatcher("/**") // 이전 Security Filter Chain 에서 검증되지 않은 모든 엔드포인트에 대해서
                .authorizeHttpRequests(authorize -> authorize.anyRequest().denyAll()) // 모두 접근 거부
                .build();
    }

    @Override
    protected RequestMatcher[] getRequestMatchers() {
        return new RequestMatcher[]{antMatcher("/api/health")};
    }
}

package team.lindo.backend.presentation.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

public abstract class SecurityConfigurationContract {
    protected ObjectMapper objectMapper;

    protected SecurityConfigurationContract(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Security 보안 공통 설정
    protected HttpSecurity configureCommonSecuritySettings(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // REST API에서는 CSRF 사용 안함
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 사용 안함
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 비활성화
                .anonymous(AbstractHttpConfigurer::disable) // Anonymous 필터 비활성화
                .rememberMe(AbstractHttpConfigurer::disable) // RememberMe 필터 비활성화
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // X-Frame-Options 헤더 허용 X
                .logout(AbstractHttpConfigurer::disable); // Logout 비활성화
    }

    // 인증이 필요한 경우 Security 보안 설정
    // TODO exceptionHandling 로직 추가
    protected HttpSecurity configureNeedsToBeAuthenticatedSecuritySettings(HttpSecurity http) throws Exception {
        return configureCommonSecuritySettings(http);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://lindo.co.kr")); // origin 설정
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // 허용할 HTTP method
        corsConfiguration.setAllowedHeaders(List.of("X-FILENAME", "X-Requested-With", "Content-Type", "BAST", "Authorization", "isFace")); // 허용할 HTTP header
        corsConfiguration.setAllowCredentials(true); // 쿠키를 주고 받을 수 있도록 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 엔드포인트에 CORS 규칙 적용

        return source;
    }

    protected abstract RequestMatcher[] getRequestMatchers();
}
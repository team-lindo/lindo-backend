package team.lindo.backend.presentation.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import team.lindo.backend.application.user.security.UserDetailService;
import team.lindo.backend.common.extensions.objectMapper.ObjectMapperExtension;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class LindoAppConfiguration {
    @PersistenceContext
    private final EntityManager entityManager;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return ObjectMapperExtension.createGlobalObjectMapper();
    }

    private final UserDetailService userDetailService;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public WebSecurityCustomizer configure() {  // 인증, 인가 서비스를 모든 곳에 적용하지 않도록 설정.
        return web -> web.ignoring()
                .requestMatchers("/static/**");  // 현재 static 하위 경로의 리소스에 적용 X
        //! .requestMatchers(toH2Console()) : h2-콘솔 url에 적용하는 부분 있었는데 여긴 H2 DB 사용 안하는데 추가했기 때문에
        // 에러 발생했었음. 그래서 코드 제거함. MySQL 관련해서는 추가해줘야 하는지 모르겠음.
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/signup", "/user/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 커스텀 로그인 페이지 url 설정
                        .defaultSuccessUrl("/home", true)
                )
//                .logout(logout -> logout
//                        .logoutUrl("/user/logout") // 로그아웃 요청 URL 지정 (기본값: "/logout")
//                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 이동할 URL
//                        .invalidateHttpSession(true) // 세션 무효화
//                )
                .logout(logout -> logout  // 위는 로그아웃시 로그인 화면으로 리디렉트. 이건 리디렉트 X 로그아웃시 200 ok 반환
                        .logoutUrl("/user.logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);  // 200 반환
                        })
                )
                .build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http
            , BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
        return builder.build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

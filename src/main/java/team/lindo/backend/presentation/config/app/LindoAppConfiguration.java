package team.lindo.backend.presentation.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @Bean  // 왠지는 몰라도 이거 안 붙여도 객체 주입 잘 되는 것 같았음. 그래도 일단 명시적으로 붙여줬음.
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
    @Bean  // 두 글 구현 다름 + 메서드 현재는 못 쓰게 해놓은 걸로 코딩된 듯
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests() // 인증, 인가 설정
                .requestMatchers("/login", "/signup", "/user").permitAll()  // 인증 없이 접근 가능
                .anyRequest().authenticated()  // 그 외에는 인증 필요하다는 뜻...?
                .and()
                .formLogin() // 폼 기반 로그인 설정 (JWT 사용할 거면 제거해야 한다? 왜..?)
                .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout() // 로그아웃 설정
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable() // csrf 보안 비활성화 -> 실습을 위해 잠깐 비활성화!!
                .build();
    }

    // 인증 관리자 관련 설정
    @Bean  // 두 글 구현 다름
    public AuthenticationManager authenticationManager(HttpSecurity http
            , BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService) // 사용자 정보 서비스 설정
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

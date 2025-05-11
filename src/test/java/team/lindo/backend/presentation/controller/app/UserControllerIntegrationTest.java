package team.lindo.backend.presentation.controller.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import team.lindo.backend.application.user.dto.LoginRequestDto;
import team.lindo.backend.application.user.dto.SignUpRequestDto;
import team.lindo.backend.application.user.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest  // spring context 전체를 로드하고, MockMvc도 여기 포함 -> @AutoConfigureMockMvc 필요X
@Transactional
public class UserControllerIntegrationTest {
    private MockMvc mockMvc;  // 단위 테스트에선 Autowired로 주입받았지만 통합 테스트에선 직접 생성해줘야 함

    @Autowired  // ObjectMapper와 WebApplicationContext는 spring이 자동으로 Bean 관리 -> Autowired로 주입
    private ObjectMapper objectMapper;

    @Autowired  // <-> ApplicationContext는 spring의 모든 빈을 관리하는 컨텍스트
    private WebApplicationContext context;  // 웹과 관련된 빈(servlet, controller 등...)을 관리하는 컨텍스트

    private final String TEST_EMAIL = "kms@example.com";
    private final String TEST_PASSWORD = "expw123";

    @BeforeEach  // 각 테스트 실행 전에 실행되는 메서드를 의미
    void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(context).build();  // 웹 컨텍스트를 기반으로 MockMvc 생성

        SignUpRequestDto signUpRequest = SignUpRequestDto.builder()
                .email(TEST_EMAIL)
                .rawPassword(TEST_PASSWORD)
                .nickname("kms")
                .build();

        mockMvc.perform(post("/user/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("회원가입 성공!"));
    }

    @Test
    @DisplayName("회원가입 후 로그인 - 성공")
    void 로그인_성공() throws Exception {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 로그인 - 실패 (잘못된 비밀번호)")
    void 로그인_실패_잘못된_비밀번호() throws Exception {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email(TEST_EMAIL)
                .password("wrongPassword")
                .build();

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("AUTH_INVALID_CREDENTIALS"))
                .andExpect(jsonPath("$.errorMessage").value("아이디 또는 비밀번호가 잘못되었습니다."))
                .andExpect(jsonPath("$.result").value("FAILURE"));
    }

    @Test
    @DisplayName("로그인 후 로그아웃 - 성공")
    void 로그아웃_성공() throws Exception{
        mockMvc.perform(post("/user/logout")
                        .with(csrf())
                        .with(user(TEST_EMAIL).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"));
    }
}

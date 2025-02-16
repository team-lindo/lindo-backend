package team.lindo.backend.presentation.controller.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.common.exception.LoginBusinessException;
import team.lindo.backend.application.user.dto.LoginRequestDto;
import team.lindo.backend.application.user.dto.LoginResponseDto;
import team.lindo.backend.application.user.dto.SignUpRequestDto;
import team.lindo.backend.application.user.service.UserService;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc  // MockMvc를 자동으로 빈으로 등록 -> @Autowired로 주입받을 수 있도록
class UserControllerTest {
    @Autowired  // MockMvc -> 컨트롤러의 API(HTTP, GET, POST 등)를 테스트하는 용도의 객체
    private MockMvc mockMvc;  // perform(http메서드)로 실행 + andExpect, andDo, andReturn 등으로 동작을 확인

    @MockBean  // 가짜 객체를 만들어 컨테이너가 빈을 주입할 수 있도록 함 (가짜 객체이니 실제 행위는 X)
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 테스트 - 성공")
    void 회원가입_성공() throws Exception {
        SignUpRequestDto request = SignUpRequestDto.builder()
                .nickname("msms")
                .email("kms@example.com")
                .rawPassword("expw123")
                .build();

        mockMvc.perform(post("/user/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("회원가입 성공!"));
    }

    @Test
    @DisplayName("로그인 테스트 - 성공")
    void 로그인_성공() throws Exception {  // 단위 테스트 - 성공 시의 상황을 가정하고 실제로 그렇게 동작하는지 체크
        LoginRequestDto request = LoginRequestDto.builder()
                .email("kms@example.com")
                .password("expw123")
                .build();

        LoginResponseDto response = LoginResponseDto.builder()
                .id(1L)
                .email("kms@example.com")
                .nickname("kms")
                .posts(Collections.emptyList())
                .followings(Collections.emptyList())
                .followers(Collections.emptyList())
                .build();

        // 아무 LoginRequestDto 요청이 userService의 login메서드로 들어오면 위에서 정의한 response를 반환하도록 설정
        given(userService.login(any(LoginRequestDto.class))).willReturn(response);

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("로그인 테스트 - 실패")
    void 로그인_실패_사용자없음() throws Exception {  // 단위 테스트 - 실패 시의 상황을 가정하고 실제로 그렇게 동작하는지 테스트
        LoginRequestDto request = LoginRequestDto.builder()
                .email("kms@example.com")
                .password("expw123")
                .build();

        given(userService.login(any(LoginRequestDto.class)))  // 요청 정보가 DB에 없는 (없는 사용자 정보 로그인) 상황 가정
                .willThrow(new LoginBusinessException("존재하지 않는 사용자입니다."));

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())  // HTTP 400 Error
                .andExpect(jsonPath("$.errorCode").value("E000"))  // BusinessExceptionHandler 코드 참고
                .andExpect(jsonPath("$.errorMessage").value("존재하지 않는 사용자입니다."))
                .andExpect(jsonPath("$.result").value("FAILURE"));
    }

    @Test
    @DisplayName("로그아웃 테스트 - 성공")
    void 로그아웃_성공() throws Exception {
        doNothing().when(userService).logout();

        mockMvc.perform(post("/user/logout")
                        .with(csrf())
                        .with(user("testUser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged out successfully")));
    }
}
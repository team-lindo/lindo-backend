package team.lindo.backend.application.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    @NotBlank(message = "이메일을 입력해주십시오.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;  //! spring security는 로그인 기본이 username-password 형식이니 이메일 말고 username으로 가능한지 소통
    //! email을 아이디로 사용할 거면 UserDetailService 수정 필요 + User의 email 필드를 unique로

    @NotBlank(message = "비밀번호를 입력해주십시오.")
    private String password;
}
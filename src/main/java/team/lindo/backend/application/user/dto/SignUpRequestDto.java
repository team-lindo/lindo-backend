package team.lindo.backend.application.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String username;  //! 기타 제약 추가하기 ex) 길이, 첫 글자 대문자, 필수 조합 등등...

    @NotBlank(message = "사용자명은 필수 입력값입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String rawPassword;
}

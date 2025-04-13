package team.lindo.backend.application.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import team.lindo.backend.application.user.entity.User;

@Getter
public class PostRequestDto {
    @NotBlank(message = "글쓴이 정보는 필수 항목입니다.")
    private User user;

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "본문은 필수 항목입니다.")
    private String content;

    @NotBlank(message = "코디 사진은 필수 항목입니다.")  // 코디 사진이 아닌 게시물 대표 이미지???
    private String imageUrl;
}

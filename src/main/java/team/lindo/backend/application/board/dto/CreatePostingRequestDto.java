package team.lindo.backend.application.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import team.lindo.backend.application.user.dto.UserSummaryDto;
import team.lindo.backend.application.user.entity.User;

import java.util.List;

@Getter
public class CreatePostingRequestDto {
    /**
     * 게시물 id (요청에서 필요한가?), 사용자 객체, 본문, 이미지 객체 리스트, 상품 객체 리스트, 댓글들(?? 필요 없지 않나?)
     *    , 생성일, 수정일(생성일 == 수정일 형태, 이거 필요한가?)
     */

    // Posting entity 이미지 여러 개 가지도록 수정 필요
    private Long id;

    private UserSummaryDto user;

    private String title;

    private String content;

//    private List<Image> images;  // 꼭 이미지를 객체로 다뤄야 하나? 이미지 url 외에 다른 정보와 함께 객체로서 다뤄야??

    private List<String> imageUrls;
}

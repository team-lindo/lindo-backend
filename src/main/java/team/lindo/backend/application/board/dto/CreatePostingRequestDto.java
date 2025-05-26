package team.lindo.backend.application.board.dto;

import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class CreatePostingRequestDto {
    /**
     * 게시물 id (요청에서 필요한가?), 사용자 객체, 본문, 이미지 객체 리스트, 상품 객체 리스트, 댓글들(?? 필요 없지 않나?)
     *    , 생성일, 수정일(생성일 == 수정일 형태, 이거 필요한가?)
     */
    private String content;
    private List<String> imageUrls;
    private List<TaggedProductGroupDto> taggedProducts;
    private Set<String> hashtags;
}

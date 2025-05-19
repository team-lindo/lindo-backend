package team.lindo.backend.application.board.dto;

import lombok.Getter;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class PostingSummaryDto {
    private Long id;
    private String content;
    private String createdAt;
    private String updateAt;
    private Set<String> hashtags;
    private List<String> imageUrls;
    private List<TaggedProductDto> taggedProducts;
    private UserSummaryDto user; // 작성자 정보
    public PostingSummaryDto(Posting posting) {
        this.id = posting.getId();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt().toString();
        this.updateAt = posting.getUpdatedAt().toString();
        this.hashtags = posting.getHashtags();
        this.imageUrls = posting.getImageUrls();
        this.user = new UserSummaryDto(posting.getUser());
        this.taggedProducts = posting.getPostingProducts().stream()
                .map(pp -> new TaggedProductDto(
                        pp.getProduct().getId(),
                        pp.getProduct().getName(),
                        pp.getProduct().getPrice(),
                        pp.getX(),
                        pp.getY()
                ))
                .toList();
    }
}

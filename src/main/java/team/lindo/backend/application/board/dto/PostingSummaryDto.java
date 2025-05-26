package team.lindo.backend.application.board.dto;

import lombok.Getter;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.PostingProduct;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class PostingSummaryDto {
    private Long id;
    private String content;
    private String createdAt;
    private String updatedAt;
    private Set<String> hashtags;
    private List<String> imageUrls;
    private List<TaggedProductGroupDto> taggedProducts;
    public PostingSummaryDto(Posting posting) {
        this.id = posting.getId();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt().toString();
        this.updatedAt = posting.getUpdatedAt().toString();
        this.hashtags = posting.getHashtags();
        this.imageUrls = posting.getImageUrls();
        Map<Long, List<TaggedProductDto>> taggedMap = posting.getPostingProducts().stream()
                .collect(Collectors.groupingBy(
                        PostingProduct::getImageId,
                        Collectors.mapping(pp -> new TaggedProductDto(
                                pp.getProduct().getId(),
                                pp.getProduct().getName(),
                                pp.getProduct().getPrice(),
                                pp.getX(),
                                pp.getY()
                        ), Collectors.toList())
                ));

        this.taggedProducts = taggedMap.entrySet().stream()
                .map(e -> new TaggedProductGroupDto(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());
    }
}

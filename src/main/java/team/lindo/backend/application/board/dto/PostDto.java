package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.PostingProduct;
import team.lindo.backend.application.user.dto.UserSummaryDto;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
// 미니 게시물
public class PostDto {
    private Long id;
    private String thumbnail;
    private UserSummaryDto user;

    public PostDto(Posting posting) {
        this.id = posting.getId();
        // imageUrls가 null이 아니고 비어있지 않으면 첫 번째 이미지 사용
        this.thumbnail = (posting.getImageUrls() != null && !posting.getImageUrls().isEmpty())
                ? posting.getImageUrls().get(0)
                : null;
        this.user = new UserSummaryDto(posting.getUser());
    }
}
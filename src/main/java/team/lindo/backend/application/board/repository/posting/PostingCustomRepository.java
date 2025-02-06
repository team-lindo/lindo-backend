package team.lindo.backend.application.board.repository.posting;

import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Posting;

import java.util.List;

public interface PostingCustomRepository {
    List<Posting> findPostingByCategoryId(Long categoryId);

    // 제목이나 내용에서 키워드가 포함되는 게시물 검색
    List<Posting> searchByTitleOrContent(@Param("keyword") String keyword);
}

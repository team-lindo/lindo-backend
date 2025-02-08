package team.lindo.backend.application.board.repository.posting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Posting;

import java.time.LocalDateTime;
import java.util.List;

public interface PostingCustomRepository {
    List<Posting> findPostingByCategoryId(Long categoryId);

    // 제목이나 내용에서 키워드가 포함되는 게시물 검색
    List<Posting> searchByTitleOrContent(@Param("keyword") String keyword);

    // 특정 카테고리 제품을 포함하는 게시물 검색
    List<Posting> findByCategoryId(@Param("categoryId") Long categoryId);

    // 좋아요순 정렬
    List<Posting> findAllByLikes();

    // 댓글 많은 순서로 정렬
    List<Posting> findAllByComments();

    // 특정 제품이 포함된 게시물 검색
    List<Posting> findByProductId(@Param("productId") Long productId);

    // 특정 기간 내의 게시물 조회
    List<Posting> findPostingsByDateRange(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    // 팔로우한 사용자의 게시물만 조회
    List<Posting> findPostingsByFollowing(@Param("userId") Long userId);

    // 특정 사용자가 작성한 게시물들 조회
    List<Posting> findPostingsByUserId(@Param("userId") Long userId);

    // 특정 사용자가 댓글을 작성한 게시물들 조회
    List<Posting> findCommentedPostingsByUserId(@Param("userId") Long userId);

    // 특정 사용자가 좋아요를 누른 게시물들 조회
    List<Posting> findLikedPostingsByUserId(Long userId);

    // 특정 사용자가 팔로우하는 사람이 작성한 게시물들 조회
    List<Posting> findFollowingPostingsByUserId(@Param("userId") Long userId);
}

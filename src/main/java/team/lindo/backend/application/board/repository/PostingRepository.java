package team.lindo.backend.application.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Posting;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    // 제목에서 특정 키워드 포함하는 게시물 검색 (제목 일부를 통해 검색)
    List<Posting> findByTitleContaining(String keyword);

    // 작성자 ID로 게시물 검색
    List<Posting> findByUserId(Long userId);

    // 작성자 닉네임으로 게시물 검색
    List<Posting> findByUserNickName(String nickName);

    // 최신순으로 게시물 필터링
    List<Posting> findAllByOrderByCreatedDesc();

    // 특정 카테고리 제품을 포함하는 게시물 검색
    @Query("""
    SELECT DISTINCT p 
    FROM Posting p
    JOIN p.postingProducts pp
    JOIN pp.product pr
    JOIN pr.productCategories pc
    WHERE pc.category.id = :categoryId
""")
    List<Posting> findByCategoryId(@Param("categoryId") Long categoryId);  // pageable 추가?

    // 좋아요순 정렬
    @Query("""
    SELECT p 
    FROM Posting p
    LEFT JOIN Like l ON l.posting = p
    GROUP BY p.id
    ORDER BY COUNT(l) DESC
""")
    List<Posting> findAllByLikes();  // left join으로 좋아요 없는 게시물들도 포함. / pageable 추가?

    // 댓글 많은 순서로 정렬
    @Query("""
    SELECT p 
    FROM Posting p
    LEFT JOIN Comment c ON c.posting = p
    GROUP BY p.id
    ORDER BY COUNT(c) DESC
""")
    List<Posting> findAllByComments();

    // 특정 제품이 포함된 게시물 검색
    @Query("""
    SELECT DISTINCT p 
    FROM Posting p
    JOIN p.postingProducts pp
    WHERE pp.product.id = :productId
""")
    List<Posting> findByProductId(@Param("productId") Long productId);

    // 특정 기간 내의 게시물 조회
    @Query("""
    SELECT p 
    FROM Posting p
    WHERE p.created BETWEEN :startDate AND :endDate
""")
    List<Posting> findPostingsByDateRange(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    // 팔로우한 사용자의 게시물만 조회
    @Query("""
    SELECT DISTINCT p
    FROM Posting p
    JOIN p.user u
    WHERE u.id IN (
        SELECT f.following.id
        FROM Follow f
        WHERE f.follower.id = :userId
    )
    ORDER BY p.created DESC
""")
    List<Posting> findPostingsByFollowing(@Param("userId") Long userId);

    // 제목이나 내용에서 키워드가 포함되는 게시물 검색
    @Query("""
    SELECT p 
    FROM Posting p
    WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) 
       OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<Posting> searchByTitleOrContent(@Param("keyword") String keyword);

    // 특정 사용자가 작성한 게시물들 조회
    @Query("""
        SELECT p 
        FROM Posting p 
        WHERE p.user.id = :userId
    """)
    List<Posting> findPostingsByUserId(@Param("userId") Long userId);

    // 특정 사용자가 좋아요를 누른 게시물들 조회
    @Query("""
        SELECT DISTINCT p 
        FROM Posting p 
        JOIN p.likes l 
        WHERE l.user.id = :userId
    """)
    List<Posting> findLikedPostingsByUserId(@Param("userId") Long userId);

    // 특정 사용자가 댓글을 작성한 게시물들 조회
    @Query("""
        SELECT DISTINCT p 
        FROM Posting p 
        JOIN p.comments c 
        WHERE c.user.id = :userId
    """)
    List<Posting> findCommentedPostingsByUserId(@Param("userId") Long userId);

    // 특정 사용자가 팔로우하는 사람이 작성한 게시물들 조회
    @Query("""
        SELECT DISTINCT p 
        FROM Posting p 
        JOIN p.user u
        JOIN Follow f ON f.following.id = u.id
        WHERE f.follower.id = :userId
    """)
    List<Posting> findFollowingPostingsByUserId(@Param("userId") Long userId);
}

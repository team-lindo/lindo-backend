package team.lindo.backend.application.board.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시물에 속한 최상위(부모) 댓글들 조회

    List<Comment> findByPostingId(@Param("postingId") Long postingId);
}

package team.lindo.backend.application.board.repository.posting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.entity.User;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long>, PostingCustomRepository {

    // 작성자 ID로 게시물 검색
    List<Posting> findByUserId(Long userId);

    // 작성자 닉네임으로 게시물 검색
    List<Posting> findByUserNickname(String nickName);

    // 최신순으로 게시물 필터링
    List<Posting> findAllByOrderByCreatedAtDesc();

    List<Posting> findByUserOrderByCreatedAtDesc(User user);
    Long countByUserId(Long userId);
}

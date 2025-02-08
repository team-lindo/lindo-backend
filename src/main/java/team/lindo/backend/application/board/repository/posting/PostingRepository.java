package team.lindo.backend.application.board.repository.posting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Posting;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long>, PostingCustomRepository {
    // 제목에서 특정 키워드 포함하는 게시물 검색 (제목 일부를 통해 검색)
    List<Posting> findByTitleContaining(String keyword);

    // 작성자 ID로 게시물 검색
    List<Posting> findByUserId(Long userId);

    // 작성자 닉네임으로 게시물 검색
    List<Posting> findByUserNickname(String nickName);

    // 최신순으로 게시물 필터링
    List<Posting> findAllByOrderByCreatedAtDesc();
}

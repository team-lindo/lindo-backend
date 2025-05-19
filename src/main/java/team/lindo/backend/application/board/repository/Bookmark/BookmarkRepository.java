package team.lindo.backend.application.board.repository.Bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.board.entity.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserIdAndPostingId(Long userId, Long postingId);

    List<Bookmark> findAllByUserId(Long userId);

    void deleteByUserIdAndPostingId(Long userId, Long postingId);
}
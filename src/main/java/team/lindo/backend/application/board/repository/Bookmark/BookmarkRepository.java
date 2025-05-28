package team.lindo.backend.application.board.repository.Bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.board.entity.Bookmark;
import team.lindo.backend.application.board.entity.Posting;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserIdAndPostingId(Long userId, Long postingId);

    @Query("SELECT b.posting FROM Bookmark b WHERE b.user.id = :userId")
    List<Posting> findBookmarkedPostingsByUserId(@Param("userId") Long userId);


    void deleteByUserIdAndPostingId(Long userId, Long postingId);
}
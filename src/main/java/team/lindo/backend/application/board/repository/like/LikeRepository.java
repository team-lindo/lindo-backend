package team.lindo.backend.application.board.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.board.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {
    Long countByPostingId(Long postingId);
}

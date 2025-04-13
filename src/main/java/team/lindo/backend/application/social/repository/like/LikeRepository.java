package team.lindo.backend.application.social.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.social.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {
}

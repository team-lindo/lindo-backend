package team.lindo.backend.application.board.repository.PostImage;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.board.entity.PostImage;

import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    Optional<PostImage> findByImageUrl(String imageUrl);
}

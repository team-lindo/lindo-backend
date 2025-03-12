package team.lindo.backend.application.wardrobe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.wardrobe.entity.WardrobeProduct;
import java.util.List;
import java.util.Optional;

public interface WardrobeProductRepository extends JpaRepository<WardrobeProduct, Long> {
    List<WardrobeProduct> findByWardrobeId(Long wardrobeId);
    Optional<WardrobeProduct> findByWardrobeIdAndProductId(Long wardrobeId, Long productId);
}

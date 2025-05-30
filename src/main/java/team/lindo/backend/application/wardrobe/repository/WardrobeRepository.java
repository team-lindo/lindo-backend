package team.lindo.backend.application.wardrobe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.wardrobe.entity.Wardrobe;

import java.util.Optional;


public interface  WardrobeRepository extends JpaRepository<Wardrobe, Long>{
    Optional<Wardrobe> findByUserId(Long userId); // 특정 옷장 조회
}

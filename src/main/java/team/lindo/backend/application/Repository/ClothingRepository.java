package team.lindo.backend.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.entity.Clothing;
import java.util.List;

public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    List<Clothing> findByCategory(Clothing.Category category);
}

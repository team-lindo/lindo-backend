package team.lindo.backend.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.product.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
}
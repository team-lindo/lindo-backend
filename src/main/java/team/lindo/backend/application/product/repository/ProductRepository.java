package team.lindo.backend.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.product.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long productId);

    Optional<Product> findByName(String productName);
}

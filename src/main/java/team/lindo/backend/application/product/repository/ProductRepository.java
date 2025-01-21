package team.lindo.backend.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

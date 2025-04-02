package team.lindo.backend.application.product.repository;

import team.lindo.backend.application.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchByQuery(String query);
}

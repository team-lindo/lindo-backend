package team.lindo.backend.application.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    private Double price;

    private String color;

    private String size;

    private String brand;

    private String siteUrl;

    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductCategory> productCategories = new HashSet<>(); // 다대다 관계 매핑

    // 개별 프로퍼티 변경 메서드
    public Product changeName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        return this;
    }

    public Product changeImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.isBlank()) {
            this.imageUrl = imageUrl;
        }
        return this;
    }

    public Product changePrice(Double price) {
        if (price != null && price > 0) {
            this.price = price;
        }
        return this;
    }

    public Product changeColor(String color) {
        if (color != null && !color.isBlank()) {
            this.color = color;
        }
        return this;
    }

    public Product changeSize(String size) {
        if (size != null && !size.isBlank()) {
            this.size = size;
        }
        return this;
    }

    public Product changeBrand(String brand) {
        if (brand != null && !brand.isBlank()) {
            this.brand = brand;
        }
        return this;
    }

    public Set<Category> getCategories() {
        return productCategories.stream()
                .map(ProductCategory::getCategory)
                .collect(Collectors.toSet());
    }
}

/**
 * 상품이 주가 아니라 코디가 주이니 상품은 최대한 간단하게 설계하는 방향으로 할까?
 *  -> 필드를 id, name, imageUrl, category만 가지도록?
 */
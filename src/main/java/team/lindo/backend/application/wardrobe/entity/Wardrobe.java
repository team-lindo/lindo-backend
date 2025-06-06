package team.lindo.backend.application.wardrobe.entity;

import jakarta.persistence.*;
import lombok.*;
import team.lindo.backend.application.common.entity.BaseEntity;
import team.lindo.backend.application.product.entity.Category;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.user.entity.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Wardrobe")
public class Wardrobe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 옷장을 소유한 사용자

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<WardrobeProduct> wardrobeProducts = new HashSet<>(); // 옷장에 있는 제품들

    public Set<WardrobeProduct> getProductsByCategory(String categoryName) {
        Set<WardrobeProduct> filteredProducts = new HashSet<>();
        for (WardrobeProduct wp : wardrobeProducts) {
            if (wp.getCategory().getName().equals(categoryName)) {
                filteredProducts.add(wp);
            }
        }
        return filteredProducts;
    }

    //  옷 추가
    public void addProduct(Product product, Category category) {
        if (alreadyContains(product)) {
            throw new IllegalArgumentException("이미 이 옷장에 추가된 제품입니다.");
        }

        wardrobeProducts.add(WardrobeProduct.create(this, product, category));
    }

    public void deleteProduct(Product product) {
        boolean deleted = wardrobeProducts.removeIf(wp -> wp.getProduct().equals(product));
        if (!deleted) {
            throw new IllegalArgumentException("해당 제품은 이 옷장에 없습니다.");
        }
    }

    // 이미 있는 옷인지 확인 (있으면 true)
    private boolean alreadyContains(Product product) {
        return wardrobeProducts.stream()
                .anyMatch(wp -> wp.getProduct().equals(product));
    }

}
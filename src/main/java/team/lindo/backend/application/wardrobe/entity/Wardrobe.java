package team.lindo.backend.application.wardrobe.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class Wardrobe {
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

        WardrobeProductId id = new WardrobeProductId(this.id, product.getId());

        WardrobeProduct wardrobeProduct = WardrobeProduct.builder()
                .id(id)
                .wardrobe(this)
                .product(product)
                .category(category)
                .build();

        wardrobeProducts.add(wardrobeProduct);
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




    // 데이터 베이스에서 옷을 가져오기 때문에 옷 정보를 수정 할 필요가 없음
    /*public void updateWardrobeProducts(Set<WardrobeProduct> products) {
        this.wardrobeProducts.clear();
        if (products != null) {
            this.wardrobeProducts.addAll(products);
        }
    }*/
}
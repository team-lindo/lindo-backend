package team.lindo.backend.application.wardrobe.entity;

import jakarta.persistence.*;
import lombok.*;
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
    public void updateWardrobeProducts(Set<WardrobeProduct> products) {
        this.wardrobeProducts.clear();
        if (products != null) {
            this.wardrobeProducts.addAll(products);
        }
    }
}
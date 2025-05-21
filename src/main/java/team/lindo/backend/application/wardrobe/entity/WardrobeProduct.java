package team.lindo.backend.application.wardrobe.entity;

import jakarta.persistence.*;
import lombok.*;
import team.lindo.backend.application.common.entity.BaseEntity;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.entity.Category;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WardrobeProduct")
public class WardrobeProduct extends BaseEntity {

    @EmbeddedId
    private WardrobeProductId id; //  복합 키 적용

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("wardrobeId")
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public static WardrobeProduct create(Wardrobe wardrobe, Product product, Category category) {
        WardrobeProductId id = new WardrobeProductId(wardrobe.getId(), product.getId());

        return WardrobeProduct.builder()
                .id(id)
                .wardrobe(wardrobe)
                .product(product)
                .category(category)
                .build();
    }
}


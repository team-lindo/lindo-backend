package team.lindo.backend.application.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.common.entity.BaseEntity;
import team.lindo.backend.application.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PostingProduct")
public class PostingProduct extends BaseEntity {
    @EmbeddedId
    private PostingProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postingId")
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    @Column(name = "image_id")
    private Long imageId;

    @Builder
    public PostingProduct(Posting posting, Product product ,double x, double y) {
        if(posting == null || product == null) {
            throw new IllegalArgumentException("게시물과 상품은 반드시 존재해야 합니다.");
        }
        this.posting = posting;
        this.product = product;
        this.x = x;
        this.y = y;
    }
}

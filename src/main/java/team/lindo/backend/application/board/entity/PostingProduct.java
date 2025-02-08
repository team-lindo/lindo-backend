package team.lindo.backend.application.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingProduct {
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

    @Builder
    public PostingProduct(Posting posting, Product product) {
        if(posting == null || product == null) {
            throw new IllegalArgumentException("게시물과 상품은 반드시 존재해야 합니다.");
        }
        this.posting = posting;
        this.product = product;
    }
}

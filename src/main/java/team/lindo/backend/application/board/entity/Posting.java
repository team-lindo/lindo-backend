package team.lindo.backend.application.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.common.entity.BaseEntity;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.social.entity.Comment;
import team.lindo.backend.application.social.entity.Like;
import team.lindo.backend.application.user.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // static 가능???

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // 게시물 작성한 회원

    private String title;

    @Lob
    private String content;  // 본문 -> 대용량 데이터 될 수 있으니 @Lob? (CLOB)

    private String imageUrl;  // 이미지 파일 경로. (로컬 서버 디렉토리 혹은 클라우드 스토리지에 이미지 파일 저장, 해당 경로)

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostingProduct> postingProducts = new HashSet<>();  // 게시물에 포함된 제품들 연결

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

//    private List<Product> recommendations = new ArrayList<>();

    public void updateTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

    public void updateContent(String content) {
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }

    public void updateImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.isBlank()) {
            this.imageUrl = imageUrl;
        }
    }

    public void updatePostingProducts(List<Product> products) {
        this.postingProducts.clear();
        if (products != null) {
            for (Product product : products) {
                this.postingProducts.add(PostingProduct.builder()
                        .posting(this)
                        .product(product)
                        .build());
            }
        }
    }
}

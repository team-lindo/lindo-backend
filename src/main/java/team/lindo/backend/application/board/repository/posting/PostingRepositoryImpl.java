package team.lindo.backend.application.board.repository.posting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.QPosting;
import team.lindo.backend.application.board.entity.QPostingProduct;
import team.lindo.backend.application.product.entity.QProduct;
import team.lindo.backend.application.product.entity.QProductCategory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingRepositoryImpl implements PostingCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final QPosting qPosting = QPosting.posting;
    private final QPostingProduct qPostingProduct = QPostingProduct.postingProduct;
    private final QProduct qProduct = QProduct.product;
    private final QProductCategory qProductCategory = QProductCategory.productCategory;

    @Override
    public List<Posting> findPostingByCategoryId(Long categoryId) {
        return jpaQueryFactory.select(qPosting)
                .from(qPosting)
                .join(qPosting.postingProducts)
                .join(qPostingProduct.product)
                .join(qProduct.productCategories)
                .where(qProductCategory.id.categoryId.eq(categoryId))
                .fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public List<Posting> searchByTitleOrContent(String keyword) {
        return jpaQueryFactory
                .selectFrom(qPosting)
                .where(
                        qPosting.title.lower().contains(keyword.toLowerCase())
                                .or(qPosting.content.lower().contains(keyword.toLowerCase()))
                )
                .fetch();
    }
}

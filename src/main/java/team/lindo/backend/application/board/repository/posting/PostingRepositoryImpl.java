package team.lindo.backend.application.board.repository.posting;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.QPosting;
import team.lindo.backend.application.board.entity.QPostingProduct;
import team.lindo.backend.application.product.entity.QProduct;
import team.lindo.backend.application.product.entity.QProductCategory;
import team.lindo.backend.application.social.entity.QComment;
import team.lindo.backend.application.social.entity.QFollow;
import team.lindo.backend.application.social.entity.QLike;
import team.lindo.backend.application.user.entity.QUser;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingRepositoryImpl implements PostingCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final QPosting qPosting = QPosting.posting;
    private final QPostingProduct qPostingProduct = QPostingProduct.postingProduct;
    private final QProduct qProduct = QProduct.product;
    private final QProductCategory qProductCategory = QProductCategory.productCategory;

    private final QLike qLike = QLike.like;
    private final QComment qComment = QComment.comment;
    private final QFollow qFollow = QFollow.follow;

    private final QUser qUser = QUser.user;

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

    @Override
    public List<Posting> findByCategoryId(Long categoryId) {
        return jpaQueryFactory
                .selectDistinct(qPosting)
                .from(qPosting)
                .join(qPosting.postingProducts, qPostingProduct)
                .join(qPostingProduct.product, qProduct)
                .join(qProduct.productCategories, qProductCategory)
                .where(qProductCategory.id.categoryId.eq(categoryId))
                .fetch();
    }

    @Override
    public List<Posting> findAllByLikes() {
        return jpaQueryFactory
                .select(qPosting)
                .from(qPosting)
                .leftJoin(qPosting.likes, qLike)
                .groupBy(qPosting.id)
                .orderBy(qLike.count().desc())
                .fetch();
    }

    @Override
    public List<Posting> findAllByComments() {
        return jpaQueryFactory
                .select(qPosting)
                .from(qPosting)
                .leftJoin(qPosting.comments, qComment)
                .groupBy(qPosting.id)
                .orderBy(qComment.count().desc())
                .fetch();
    }

    @Override
    public List<Posting> findByProductId(Long productId) {
        return jpaQueryFactory
                .selectDistinct(qPosting)
                .from(qPosting)
                .join(qPosting.postingProducts, qPostingProduct)
                .where(qPostingProduct.product.id.eq(productId))
                .fetch();
    }

    @Override
    public List<Posting> findPostingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory
                .select(qPosting)
                .from(qPosting)
                .where(qPosting.createdAt.between(startDate, endDate))
                .fetch();
    }

    @Override
    public List<Posting> findPostingsByFollowing(Long userId) {
        return jpaQueryFactory
                .selectDistinct(qPosting)
                .from(qPosting)
                .join(qPosting.user, qUser)
                .where(qUser.id.in(
                        JPAExpressions.select(qFollow.following.id)
                                .from(qFollow)
                                .where(qFollow.follower.id.eq(userId))
                ))
                .orderBy(qPosting.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Posting> findPostingsByUserId(Long userId) {
        return jpaQueryFactory
                .select(qPosting)
                .from(qPosting)
                .where(qPosting.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Posting> findLikedPostingsByUserId(Long userId) {
        return jpaQueryFactory
                .selectDistinct(qPosting)
                .from(qPosting)
                .join(qPosting.likes, qLike)
                .where(qLike.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Posting> findCommentedPostingsByUserId(Long userId) {
        return jpaQueryFactory
                .selectDistinct(qPosting)
                .from(qPosting)
                .join(qPosting.comments, qComment)
                .where(qComment.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Posting> findFollowingPostingsByUserId(Long userId) {
        return jpaQueryFactory
                .selectDistinct(qPosting)
                .from(qPosting)
                .join(qPosting.user, qUser)
                .join(qFollow)
                .on(qFollow.following.id.eq(qUser.id))
                .where(qFollow.follower.id.eq(userId))
                .fetch();
    }
}

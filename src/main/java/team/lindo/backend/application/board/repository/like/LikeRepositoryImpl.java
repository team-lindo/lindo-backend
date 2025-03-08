package team.lindo.backend.application.board.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.lindo.backend.application.board.entity.Like;
import team.lindo.backend.application.social.entity.QLike;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    private final QLike qLike = QLike.like;

    @Override
    public Optional<Like> findByUserIdAndPostingId(Long userId, Long postingId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qLike)
                        .where(qLike.user.id.eq(userId).and(qLike.posting.id.eq(postingId)))
                        .fetchOne()
        );
    }

    @Override
    public Long countByPostingId(Long postingId) {
        return jpaQueryFactory
                .select(qLike.count())
                .from(qLike)
                .where(qLike.posting.id.eq(postingId))
                .fetchOne();
    }
}

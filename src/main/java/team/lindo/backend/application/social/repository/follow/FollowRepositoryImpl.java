package team.lindo.backend.application.social.repository.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.lindo.backend.application.social.entity.Follow;

import java.util.List;
import java.util.Optional;

import static team.lindo.backend.application.social.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Follow> findByFollowerId(Long followerId) {
        return queryFactory
                .selectFrom(follow)
                .where(follow.follower.id.eq(followerId))
                .fetch();  // 결과 반환
    }

    @Override
    public List<Follow> findByFollowingId(Long followingId) {
        return queryFactory
                .selectFrom(follow)
                .where(follow.following.id.eq(followingId))
                .fetch();
    }

    @Override
    public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(follow)
                        .where(follow.follower.id.eq(followerId).and(follow.following.id.eq(followingId)))
                        .limit(1)  // 다중 결과 방지
                        .fetchOne()
        );
    }

    @Override
    public Long countByFollowerId(Long followerId) {
        return queryFactory
                .select(follow.count())  // SQL COUNT()
                .from(follow)
                .where(follow.follower.id.eq(followerId))
                .fetchOne();
    }

    @Override
    public Long countByFollowingId(Long followingId) {
        return queryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.following.id.eq(followingId))
                .fetchOne();
    }
}

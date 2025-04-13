package team.lindo.backend.application.social.repository.follow;

import team.lindo.backend.application.social.entity.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowCustomRepository {
    // 특정 사용자의 팔로잉 목록 조회
    public List<Follow> findByFollowerId(Long followerId);

    // 특정 사용자의 팔로워 목록 조회
    public List<Follow> findByFollowingId(Long followingId);

    // 특정 팔로우 관계 조회
    public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // 특정 사용자가 팔로우하는 사람의 수 조회
    public Long countByFollowerId(Long followerId);

    // 특정 사용자의 팔로워 수 조회
    public Long countByFollowingId(Long followingId);
}

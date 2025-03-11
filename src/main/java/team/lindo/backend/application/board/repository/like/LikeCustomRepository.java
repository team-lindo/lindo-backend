package team.lindo.backend.application.board.repository.like;

import team.lindo.backend.application.board.entity.Like;

import java.util.Optional;

public interface LikeCustomRepository {
    // 특정 사용자가 특정 게시물에 좋아요를 눌렀는지 확인
    public Optional<Like> findByUserIdAndPostingId(Long userId, Long postingId);

    // 특정 게시물의 좋아요 개수 조회
    public Long countByPostingId(Long postingId);
}

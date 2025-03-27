package team.lindo.backend.application.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.board.entity.Like;
import team.lindo.backend.application.board.repository.like.LikeRepository;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    // 좋아요 추가
    @Transactional
    public Like addLike(Long userId, Long postingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        likeRepository.findByUserIdAndPostingId(userId, postingId)
                .ifPresent(l -> {
                    throw new IllegalArgumentException("이미 좋아요를 누른 게시물입니다.");
                });

        return likeRepository.save(Like.builder()
                .user(user)
                .posting(posting)
                .build());
    }

    // 좋아요 취소
    @Transactional
    public void removeLike(Long userId, Long postingId) {
        Like like = likeRepository.findByUserIdAndPostingId(userId, postingId)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누른 기록이 없습니다."));

        likeRepository.delete(like);
    }

    // 특정 게시물의 좋아요 개수 조회
    @Transactional(readOnly = true)
    public Long getLikeCount(Long postingId) {
        return likeRepository.countByPostingId(postingId);
    }

    // 특정 사용자가 특정 게시물에 좋아요를 눌렀는지 확인
    @Transactional(readOnly = true)
    public Boolean hasLiked(Long userId, Long postingId) {
        return likeRepository.findByUserIdAndPostingId(userId, postingId).isPresent();
    }
}

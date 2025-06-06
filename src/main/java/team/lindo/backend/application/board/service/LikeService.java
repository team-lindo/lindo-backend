package team.lindo.backend.application.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.board.dto.PostDto;
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
    public PostDto addLike(Long userId, Long postingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("Post does not exist."));

        likeRepository.findByUserIdAndPostingId(userId, postingId)
                .ifPresent(l -> {
                    throw new IllegalArgumentException("This post has already been liked.");
                });

        Like like = Like.builder()
                .user(user)
                .build();

        posting.addLike(like);  // 양방향 관계 유지 (builder에서 .posting(posting) 해주던 걸 편의 매서드로)
        likeRepository.save(like);

        return new PostDto(posting);
    }

    // 좋아요 취소
    @Transactional
    public void removeLike(Long userId, Long postingId) {
        Like like = likeRepository.findByUserIdAndPostingId(userId, postingId)
                .orElseThrow(() -> new IllegalArgumentException("There is no record of pressing like."));

        // likeRepository.delete(like)는 remove 메서드만 호출하면 트랜잭션 commit 시점에 JPA가 자동으로 delete query 실행해줌
        like.getPosting().removeLike(like);
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

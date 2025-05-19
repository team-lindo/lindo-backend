package team.lindo.backend.application.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.board.dto.PostDto;
import team.lindo.backend.application.board.entity.Bookmark;
import team.lindo.backend.application.board.entity.Like;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.repository.Bookmark.BookmarkRepository;
import team.lindo.backend.application.board.repository.like.LikeRepository;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    // 좋아요 추가
    @Transactional
    public PostDto addBookmark(Long userId, Long postingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        bookmarkRepository.findByUserIdAndPostingId(userId, postingId)
                .ifPresent(l -> {
                    throw new IllegalArgumentException("이미 북마크를 누른 게시물입니다.");
                });

        bookmarkRepository.save(new Bookmark(user, posting));
        return new PostDto(posting);
    }

    // 좋아요 취소
    @Transactional
    public void removeBookmark(Long userId, Long postId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndPostingId(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("북마크한 적이 없습니다."));

        bookmarkRepository.delete(bookmark);
    }
}
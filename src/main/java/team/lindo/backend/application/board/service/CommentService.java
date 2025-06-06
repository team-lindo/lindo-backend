package team.lindo.backend.application.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.board.entity.Comment;
import team.lindo.backend.application.board.repository.comment.CommentRepository;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    // 댓글 생성
    @Transactional
    public Comment createComment(Long postingId, Long userId, String content) {
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));


        Comment comment = Comment.builder()
                .user(user)
                .content(content)
                .build();

        posting.addComment(comment);

        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if(newContent != null && !newContent.isBlank()) {
            comment = Comment.builder()
                    .id(comment.getId())
                    .posting(comment.getPosting())
                    .user(comment.getUser())
                    .content(newContent)
                    .build();
        }
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        comment.getPosting().removeComment(comment);  // JPA가 commentRepository.delete(comment); 대신 delete query 날려줌
    }


}

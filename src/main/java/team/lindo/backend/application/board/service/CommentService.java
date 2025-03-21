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

    // 댓글 생성 (!GPT 확인하기)
    @Transactional
    public Comment createComment(Long postingId, Long userId, String content, Long parentCommentId) {
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Comment parentComment = null;
        if(parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                .posting(posting)
                .user(user)
                .content(content)
                .parentComment(parentComment)
                .build();

        if(parentComment != null) {
            parentComment.addChildComment(comment);
        }

        return commentRepository.save(comment);
    }

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
                    .parentComment(comment.getParentComment())
                    .childComments(comment.getChildComments())
                    .build();
        }
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }

    // 특정 게시물의 최상위(부모) 댓글들 조회
    public List<Comment> getParentCommentsByPosting(Long postingId) {
        return commentRepository.findParentCommentsByPostingId(postingId);
    }

    // 특정 부모 댓글의 답글들 조회
    public List<Comment> getChildCommentsByParentComment(Long parentCommentId) {
        return commentRepository.findChildCommentsByParentCommentId(parentCommentId);
    }
}

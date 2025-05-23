package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.lindo.backend.application.board.dto.*;
import team.lindo.backend.application.board.entity.Comment;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.service.BookmarkService;
import team.lindo.backend.application.board.service.CommentService;
import team.lindo.backend.application.board.service.LikeService;
import team.lindo.backend.application.board.service.PostingService;
import team.lindo.backend.application.board.dto.DeletePostResponseDto;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.security.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final BookmarkService bookmarkService;
//    private final S3Service s3Service;  // AWS S3 사용 -> 나중에 추가 필요 (이미지 업로드)

    @PostMapping("/post")
    public ResponseEntity<PostingSummaryDto> createPosting(@RequestBody CreatePostingRequestDto request, @AuthenticationPrincipal User user) {
        Posting posting = postingService.createPosting(request, user);  //? @AuthenticationPrincipal 뭔지 알아보기
        return ResponseEntity.ok(new PostingSummaryDto(posting));
    }

    @PatchMapping("/post/{postingId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostingRequestDto request) {
        Posting updated = postingService.update(postId, request.getContent());
        return ResponseEntity.ok(new UpdatePostResponseDto(updated));
    }

    @PostMapping ("/post/{postId}/upload/images")
    public ResponseEntity<List<UploadImageResponseDto>> uploadImages(
            @RequestParam("images") MultipartFile[] images) {

        List<UploadImageResponseDto> uploaded = postingService.uploadImages(images);
        return ResponseEntity.ok(uploaded);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<DeletePostResponseDto> deletePosting(@PathVariable Long postingId) {
        postingService.deletePosting(postingId);
        DeletePostResponseDto response = new DeletePostResponseDto(postingId.toString());
        return ResponseEntity.ok(response);
    }

    //특정 개시물 상세 조회
    @GetMapping("post/{postId}")
    public ResponseEntity<LoadPostResponseDto> loadPost(@PathVariable Long id) {
        LoadPostResponseDto response = postingService.loadPost(id);
        return ResponseEntity.ok(response);
    }
    // 게시물에 댓글 추가
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<AddCommentResponseDto> addComment(
            @PathVariable Long postId,
            @RequestBody AddCommentRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getId();

        Comment comment = commentService.createComment(postId, userId, request.getContent(), null);

        return ResponseEntity.ok(
                AddCommentResponseDto.builder()
                        .postId(postId.toString())
                        .comment(new CommentDto(comment))
                        .build()
        );
    }
    // 게시물 좋아요 추가
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<PostDto> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getId();
        PostDto response = likeService.addLike(postId, userId);
        return ResponseEntity.ok(response);
    }

    //게시물 좋아요 취소
    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<String> unLikePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getId();
        likeService.removeLike(postId, userId);

        return ResponseEntity.ok(postId.toString());
    }

    @PostMapping("/post/{postId}/bookmark")
    public ResponseEntity<PostDto> bookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(
                bookmarkService.addBookmark(userDetails.getId(), postId)
        );
    }

    @DeleteMapping("/post/{postId}/bookmark")
    public ResponseEntity<String> unBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();
        bookmarkService.removeBookmark(postId, userId);
        return ResponseEntity.ok(postId.toString());
    }

    // 게시물 10개 보이고 내리면 10개씩 무한으로 보임
    @GetMapping("/posts")
    public ResponseEntity<PostPageResponseDto> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(postingService.getPostPreviews(pageable));
    }

   /* public ResponseEntity<List<PostingSummaryDto>> searchPostings(String keyword) {
        List<PostingSummaryDto> postings = postingService.searchPostingsByKeyword(keyword);
        return ResponseEntity.ok(postings);
    }*/

    // 나중에 추가 필요한 부분 (이미지 업로드)
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        String imageUrl = s3Service.uploadFile(file);
//        return ResponseEntity.ok(imageUrl);
//    }
}

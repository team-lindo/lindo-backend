package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.board.dto.CreatePostingRequestDto;
import team.lindo.backend.application.board.dto.PostingSummaryDto;
import team.lindo.backend.application.board.dto.UpdatePostingRequestDto;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.service.PostingService;
import team.lindo.backend.application.user.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;
//    private final S3Service s3Service;  // AWS S3 사용 -> 나중에 추가 필요 (이미지 업로드)

    @PostMapping("/create/posting")
    public ResponseEntity<PostingSummaryDto> createPosting(@RequestBody CreatePostingRequestDto request, @AuthenticationPrincipal User user) {
        Posting posting = postingService.createPosting(request, user);  //? @AuthenticationPrincipal 뭔지 알아보기
        return ResponseEntity.ok(new PostingSummaryDto(posting));
    }

    @GetMapping("/get")
    public ResponseEntity<Page<PostingSummaryDto>> getAllPostings(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostingSummaryDto> postings = postingService.getAllPostings(page, size);
        return ResponseEntity.ok(postings);
    }

    @GetMapping("/get/{postingId}")
    public ResponseEntity<PostingSummaryDto> getPosting(@PathVariable Long postingId) {
        PostingSummaryDto posting = postingService.getPostingById(postingId);
        return ResponseEntity.ok(posting);
    }

    @PostMapping("/update/{postingId}")
    public ResponseEntity<PostingSummaryDto> updatePosting(@PathVariable Long postingId, @RequestBody UpdatePostingRequestDto request) {
        PostingSummaryDto updatedPosting = postingService.updatePosting(postingId, request);
        return ResponseEntity.ok(updatedPosting);
    }

    @DeleteMapping("/delete/{postingId}")
    public ResponseEntity<Void> deletePosting(@PathVariable Long postingId) {
        postingService.deletePosting(postingId);
        return ResponseEntity.noContent().build();  //? 이 부분 알아보기
    }

    public ResponseEntity<List<PostingSummaryDto>> searchPostings(String keyword) {
        List<PostingSummaryDto> postings = postingService.searchPostingsByKeyword(keyword);
        return ResponseEntity.ok(postings);
    }

    // 나중에 추가 필요한 부분 (이미지 업로드)
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        String imageUrl = s3Service.uploadFile(file);
//        return ResponseEntity.ok(imageUrl);
//    }
}

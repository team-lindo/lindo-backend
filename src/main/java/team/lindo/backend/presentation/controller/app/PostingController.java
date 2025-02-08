package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.board.dto.PostRequestDto;
import team.lindo.backend.application.board.dto.PostingResponseDto;
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
    public ResponseEntity<PostingResponseDto> createPosting(@RequestBody PostRequestDto request, @AuthenticationPrincipal User user) {
        Posting posting = postingService.createPosting(request, user);  //? @AuthenticationPrincipal 뭔지 알아보기
        return ResponseEntity.ok(new PostingResponseDto(posting));
    }

    @GetMapping("get")
    public ResponseEntity<Page<PostingResponseDto>> getAllPostings(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostingResponseDto> postings = postingService.getAllPostings(page, size);
        return ResponseEntity.ok(postings);
    }

    @GetMapping("/get/{postingId}")
    public ResponseEntity<PostingResponseDto> getPosting(@PathVariable Long postingId) {
        PostingResponseDto posting = postingService.getPostingById(postingId);
        return ResponseEntity.ok(posting);
    }

    @PostMapping("/update/{postingId}")
    public ResponseEntity<PostingResponseDto> updatePosting(@PathVariable Long postingId, @RequestBody UpdatePostingRequestDto request) {
        PostingResponseDto updatedPosting = postingService.updatePosting(postingId, request);
        return ResponseEntity.ok(updatedPosting);
    }

    @DeleteMapping("/delete/{postingId}")
    public ResponseEntity<Void> deletePosting(@PathVariable Long postingId) {
        postingService.deletePosting(postingId);
        return ResponseEntity.noContent().build();  //? 이 부분 알아보기
    }

    public ResponseEntity<List<PostingResponseDto>> searchPostings(String keyword) {
        List<PostingResponseDto> postings = postingService.searchPostingsByKeyword(keyword);
        return ResponseEntity.ok(postings);
    }

    // 나중에 추가 필요한 부분 (이미지 업로드)
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        String imageUrl = s3Service.uploadFile(file);
//        return ResponseEntity.ok(imageUrl);
//    }
}

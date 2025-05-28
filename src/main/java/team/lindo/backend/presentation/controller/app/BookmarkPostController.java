package team.lindo.backend.presentation.controller.app;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.application.board.dto.BookmarkedPostSummaryDto;
import team.lindo.backend.application.board.service.PostingService;
import team.lindo.backend.application.user.security.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app/bookmarked-posts")
@RequiredArgsConstructor
public class BookmarkPostController {

    private final PostingService postingService;
    @GetMapping()
    public ResponseEntity<List<BookmarkedPostSummaryDto>> getBookmarkedPosts(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        List<BookmarkedPostSummaryDto> posts = postingService.getBookmarkedPosts(userId);
        return ResponseEntity.ok(posts);
    }
}

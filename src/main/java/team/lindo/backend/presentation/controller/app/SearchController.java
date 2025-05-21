package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.product.service.ProductSearchService;
import team.lindo.backend.application.search.dto.SearchResponseDto;
import team.lindo.backend.application.search.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponseDto> search(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.search(keyword));
    }
}
package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.product.service.ProductSearchService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchDto>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(productSearchService.search(query));
    }
}
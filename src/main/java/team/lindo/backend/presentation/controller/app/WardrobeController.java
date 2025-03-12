package team.lindo.backend.presentation.controller.app;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.wardrobe.dto.WardrobeProductDto;
import team.lindo.backend.application.wardrobe.service.WardrobeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wardrobe")
@RequiredArgsConstructor
public class WardrobeController {

    private final WardrobeService wardrobeService;

    // 카테고리별 옷 목록 조회
    @GetMapping("/{wardrobeId}/products/grouped")
    public ResponseEntity<Map<String, List<WardrobeProductDto>>> getWardrobeProductsGrouped(
            @PathVariable Long wardrobeId) {
        return ResponseEntity.ok(wardrobeService.getProductsGroupedByRootCategory(wardrobeId));
    }

    //  옷 상세 조회
    @GetMapping("/{wardrobeId}/products/{productId}")
    public ResponseEntity<WardrobeProductDto> getWardrobeProductDetail(
            @PathVariable Long wardrobeId, @PathVariable Long productId) {
        return ResponseEntity.ok(wardrobeService.getWardrobeProductDetail(wardrobeId, productId));
    }

    //  옷 추가
    @PostMapping("/{wardrobeId}/products/{productId}")
    public ResponseEntity<Void> addProductToWardrobe(
            @PathVariable Long wardrobeId, @PathVariable Long productId,
            @RequestParam Long categoryId) { // categoryId를 받고 서비스에서 최상위 카테고리로 자동 변환
        wardrobeService.addProductToWardrobe(wardrobeId, productId, categoryId);
        return ResponseEntity.ok().build();
    }

    //  옷 삭제
    @DeleteMapping("/{wardrobeId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long wardrobeId, @PathVariable Long productId) {
        wardrobeService.deleteProductFromWardrobe(wardrobeId, productId);
        return ResponseEntity.ok().build();
    }
}

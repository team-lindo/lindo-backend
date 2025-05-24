package team.lindo.backend.presentation.controller.app;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.product.dto.ProductDto;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.user.security.CustomUserDetails;
import team.lindo.backend.application.wardrobe.dto.AddProductRequestDto;
import team.lindo.backend.application.wardrobe.dto.DeleteProductResponseDto;
import team.lindo.backend.application.wardrobe.dto.FetchClosetResponseDto;
import team.lindo.backend.application.wardrobe.dto.WardrobeProductDto;
import team.lindo.backend.application.wardrobe.service.WardrobeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app/closet")
@RequiredArgsConstructor
public class WardrobeController {

    private final WardrobeService wardrobeService;

   /* // 카테고리별 옷 목록 조회
    @GetMapping("/{wardrobeId}/products/grouped")
    public ResponseEntity<Map<String, List<WardrobeProductDto>>> getWardrobeProductsGrouped(
            @PathVariable Long wardrobeId) {
        return ResponseEntity.ok(wardrobeService.getProductsGroupedByCategory(wardrobeId));
    }*/

    //옷장 옷 모두 보기 (옷장 기본)
    @GetMapping("/me")
    public ResponseEntity<FetchClosetResponseDto> getMyCloset(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        FetchClosetResponseDto response = wardrobeService.fetchMyCloset(userDetails.getId());
        return ResponseEntity.ok(response);
    }

    //  옷 추가
    @PostMapping("/me/product")
    public ResponseEntity<ProductDto> addProductToMyWardrobe(
            @RequestBody AddProductRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ProductDto response = wardrobeService.addProductByInfo(userDetails.getId(), requestDto);
        return ResponseEntity.ok(response);
    }

    //  옷 삭제
    @DeleteMapping("/me/product/{productId}")
    public ResponseEntity<DeleteProductResponseDto> deleteProductFromMyWardrobe(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String deletedId = wardrobeService.deleteProduct(userDetails.getId(), productId);
        DeleteProductResponseDto response = new DeleteProductResponseDto(deletedId);
        return ResponseEntity.ok(response);
    }

    //  옷 상세 조회
    @GetMapping("/me/product/{productId}")
    public ResponseEntity<ProductDto> getMyWardrobeProductDetail(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ProductDto response = wardrobeService.getProductDetail(userDetails.getId(), productId);
        return ResponseEntity.ok(response);
    }

    /*//  옷장 안 옷 검색
    @GetMapping("/{wardrobeId}/products/search")
    public ResponseEntity<List<ProductSearchDto>> searchProductsInWardrobe(
            @PathVariable Long wardrobeId,
            @RequestParam String query) {
        return ResponseEntity.ok(wardrobeService.searchProductsInWardrobe(wardrobeId, query));
    }*/
}

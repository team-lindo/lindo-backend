package team.lindo.backend.presentation.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.lindo.backend.application.product.dto.ProductInfoDto;
import team.lindo.backend.application.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/app/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfoDto> getProductInfo(@PathVariable Long productId) {
        ProductInfoDto productInfo = productService.getProductInfos(productId);

        return ResponseEntity.ok(productInfo);
    }
}

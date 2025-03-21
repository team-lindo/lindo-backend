package team.lindo.backend.application.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 제품 추가
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 제품 수정
    @Transactional
    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품이 존재하지 않습니다."));

        return existingProduct
                .changeName(updatedProduct.getName())
                .changePrice(updatedProduct.getPrice())
                .changeImageUrl(updatedProduct.getImageUrl())
//                .changeSize(updatedProduct.getSize())
//                .changeColor(updatedProduct.getColor())
                .changeBrand(updatedProduct.getBrand());
    }

    // 제품 삭제
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    /**
     * 그 외 특정 브랜드의 제품 검색, 특정 금액대의 제품 검색 등등의 기능은 일부러 구현하지 않았음.
     * 코디 공유 사이트이기 때문에 사용자가 제품을 검색할 일이 없다고 생각했기 때문.
     * 특정 브랜드의 제품 검색 X -> 특정 브랜드의 제품이 포함된 게시물 검색이 맞다고 생각했기 때문.
     */
}

package team.lindo.backend.application.wardrobe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.matcher.ProductMatchScorer;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.wardrobe.dto.WardrobeProductDto;
import team.lindo.backend.application.wardrobe.entity.Wardrobe;
import team.lindo.backend.application.wardrobe.entity.WardrobeProduct;
import team.lindo.backend.application.wardrobe.entity.WardrobeProductId;
import team.lindo.backend.application.wardrobe.repository.WardrobeProductRepository;
import team.lindo.backend.application.wardrobe.repository.WardrobeRepository;
import team.lindo.backend.application.product.repository.ProductRepository;
import team.lindo.backend.application.product.repository.CategoryRepository;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.entity.Category;


import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WardrobeService {

    private final WardrobeRepository wardrobeRepository;
    private final WardrobeProductRepository wardrobeProductRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMatchScorer matchScorer;



    @Transactional(readOnly = true)
    public Map<String, List<WardrobeProductDto>> getProductsGroupedByCategory(Long wardrobeId) {
        List<WardrobeProduct> wardrobeProducts = wardrobeProductRepository.findByWardrobeId(wardrobeId);

        return wardrobeProducts.stream()
                .collect(Collectors.groupingBy(
//                        wp -> wp.getCategory().getRootCategory().getName(), // 최상위 카테고리로 그룹화
                        wp -> wp.getCategory().getName(), // api용 카테고리
                        Collectors.mapping(this::convertToDto, Collectors.toList())
                ));
    }

    //  옷 상세 조회
    @Transactional(readOnly = true)
    public WardrobeProductDto getWardrobeProductDetail(Long wardrobeId, Long productId) {
        WardrobeProduct wardrobeProduct = wardrobeProductRepository.findByWardrobeIdAndProductId(wardrobeId, productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷을 찾을 수 없습니다."));

        return convertToDto(wardrobeProduct);
    }


    //  옷 추가
    public void addProductToWardrobe(Long wardrobeId, Long productId, Long categoryId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷장을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품을 찾을 수 없습니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        wardrobe.addProduct(product, category);

        wardrobeRepository.save(wardrobe); // 변경 감지로 저장됨
    }

    //  옷 삭제
    public void deleteProductFromWardrobe(Long wardrobeId, Long productId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷장을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품을 찾을 수 없습니다."));

        wardrobe.deleteProduct(product);

        wardrobeRepository.save(wardrobe);
    }

    // 옷장에 옷 검색 기능
    @Transactional(readOnly = true)
    public List<ProductSearchDto> searchProductsInWardrobe(Long wardrobeId, String query) {
        List<WardrobeProduct> wardrobeProducts = wardrobeProductRepository.findByWardrobeId(wardrobeId);

        return wardrobeProducts.stream()
                .map(wp -> new AbstractMap.SimpleEntry<>(wp.getProduct(), matchScorer.calculateMatchScore(wp.getProduct(), query)))
                .filter(entry -> entry.getValue() > 0)
                .sorted((a, b) -> b.getValue() - a.getValue()) // 유사도 높은 순 정렬
                .map(entry -> ProductSearchDto.from(entry.getKey()))
                .collect(Collectors.toList()); // 전체 결과 반환
    }

    private WardrobeProductDto convertToDto(WardrobeProduct wardrobeProduct) {
        return new WardrobeProductDto(
                wardrobeProduct.getWardrobe().getId(),
                wardrobeProduct.getProduct().getId(),
                wardrobeProduct.getProduct().getName(),
                wardrobeProduct.getProduct().getImageUrl(),
               // wardrobeProduct.getCategory().getRootCategory().getName() //  최상위 카테고리 적용
                wardrobeProduct.getCategory().getName() // api용 카테고리

        );
    }
}

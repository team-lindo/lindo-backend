package team.lindo.backend.application.wardrobe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.search.service.ProductMatchScorer;
import team.lindo.backend.application.product.dto.ProductDto;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.wardrobe.dto.AddProductRequestDto;
import team.lindo.backend.application.wardrobe.dto.FetchClosetResponseDto;
import team.lindo.backend.application.wardrobe.dto.WardrobeProductDto;
import team.lindo.backend.application.wardrobe.entity.Wardrobe;
import team.lindo.backend.application.wardrobe.entity.WardrobeProduct;
import team.lindo.backend.application.wardrobe.repository.WardrobeProductRepository;
import team.lindo.backend.application.wardrobe.repository.WardrobeRepository;
import team.lindo.backend.application.product.repository.ProductRepository;
import team.lindo.backend.application.product.repository.CategoryRepository;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.entity.Category;


import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    // 옷장 전체 옷 보여주기
    @Transactional(readOnly = true)
    public FetchClosetResponseDto fetchMyCloset(Long userId) {
        Wardrobe wardrobe = wardrobeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 옷장을 찾을 수 없습니다."));

        List<ProductDto> items = wardrobe.getWardrobeProducts().stream()
                .map(wp -> new ProductDto(wp.getProduct()))
                .toList();

        return new FetchClosetResponseDto(items);
    }

    //  옷 상세 조회
    @Transactional(readOnly = true)
    public ProductDto getProductDetail(Long userId, Long productId) {
        Wardrobe wardrobe = wardrobeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 옷장을 찾을 수 없습니다."));

        // 옷장에 이 제품이 존재하는지 확인
        boolean exists = wardrobe.getWardrobeProducts().stream()
                .anyMatch(wp -> wp.getProduct().getId().equals(productId));

        if (!exists) {
            throw new IllegalArgumentException("해당 옷은 옷장에 존재하지 않습니다.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품 정보를 찾을 수 없습니다."));

        return new ProductDto(product);
    }
    // 옷 정보로 옷장에 옷 추가
    @Transactional
    public ProductDto  addProductByInfo(Long userId, AddProductRequestDto dto) {
        Wardrobe wardrobe = wardrobeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 옷장을 찾을 수 없습니다."));

        Optional<Product> optionalProduct = productRepository.findAll().stream()
                .filter(p -> p.getName().equalsIgnoreCase(dto.getProductName()) &&
                        p.getBrand().equalsIgnoreCase(dto.getBrand()) &&
                        p.getCategory().getName().equalsIgnoreCase(dto.getCategory()))
                .findFirst();

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("일치하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        Category category = product.getCategory();

        wardrobe.addProduct(product, category);
        wardrobeRepository.save(wardrobe);

        return new ProductDto(product);
    }


    /*//  옷 추가
    public void addProductToWardrobe(Long userId, Long productId) {
        Wardrobe wardrobe = wardrobeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷장을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품을 찾을 수 없습니다."));

        Category category = product.getCategory();

        wardrobe.addProduct(product, category);

        wardrobeRepository.save(wardrobe); // 변경 감지로 저장됨
    }*/

    //옷장에서 옷 삭제
    @Transactional
    public String deleteProduct(Long userId, Long productId) {
        Wardrobe wardrobe = wardrobeRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 옷장을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품을 찾을 수 없습니다."));

        wardrobe.deleteProduct(product);
        wardrobeRepository.save(wardrobe);

        return productId.toString();
    }

    //  옷 삭제
    /*public void deleteProductFromWardrobe(Long wardrobeId, Long productId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷장을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품을 찾을 수 없습니다."));

        wardrobe.deleteProduct(product);

        wardrobeRepository.save(wardrobe);
    }*/

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

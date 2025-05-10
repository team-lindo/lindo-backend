package team.lindo.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.product.entity.Category;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.repository.CategoryRepository;
import team.lindo.backend.application.product.repository.ProductRepository;
import team.lindo.backend.application.product.service.ProductSearchService;
import team.lindo.backend.application.user.entity.Role;
import team.lindo.backend.application.wardrobe.dto.WardrobeProductDto;
import team.lindo.backend.application.wardrobe.entity.Wardrobe;
import team.lindo.backend.application.wardrobe.repository.WardrobeProductRepository;
import team.lindo.backend.application.wardrobe.repository.WardrobeRepository;
import team.lindo.backend.application.wardrobe.service.WardrobeService;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("옷장 검색 및 추가 통합 테스트")
public class WardrobeSearchIntegrationTest {

    @Autowired
    private ProductSearchService productSearchService;

    @Autowired
    private WardrobeService wardrobeService;

    @Autowired
    private WardrobeProductRepository wardrobeProductRepository;

    @Autowired
    private WardrobeRepository wardrobeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private Long wardrobeId;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .email("testuser@example.com")
                .nickname("테스트유저")
                .password("test1234") // 테스트에서는 평문 OK, 보안 X
                .role(Role.USER)      // enum 값 확인!
                .profileImageUrl(null)
                .build();
        userRepository.save(user);

        Wardrobe wardrobe = Wardrobe.builder()
                .user(user)
                .build();
        wardrobeRepository.save(wardrobe);

        wardrobeId = wardrobe.getId();
    }

    @Test
    @DisplayName("'게스' 키워드 검색 결과 확인")
    public void testSearchByKeyword() {
        String keyword = "게스";
        List<ProductSearchDto> searchResults = productSearchService.search(keyword);

        assertFalse(searchResults.isEmpty(), "검색 결과가 있어야 함");
        System.out.println("검색 결과 수: " + searchResults.size());
        System.out.println("첫 번째 상품 이름: " + searchResults.get(0).getName());
    }

    @Test
    @DisplayName("상품을 옷장에 추가한 후 실제로 포함되어 있는지 확인")
    public void testAddProductAndVerifyInWardrobe() {
        String keyword = "게스";
        List<ProductSearchDto> searchResults = productSearchService.search(keyword);

        assertFalse(searchResults.isEmpty(), "검색 결과가 있어야 함");
        ProductSearchDto topProduct = searchResults.get(0);

        wardrobeService.addProductToWardrobe(
                wardrobeId,
                topProduct.getId(),
                topProduct.getCategoryId()
        );

        WardrobeProductDto result = wardrobeService.getWardrobeProductDetail(wardrobeId, topProduct.getId());

        assertNotNull(result, "옷장에서 상품을 조회할 수 있어야 함");
        assertEquals(topProduct.getId(), result.getProductId(), "상품 ID가 일치해야 함");
        System.out.println("옷장에 추가된 상품 이름: " + result.getProductName());
    }
}

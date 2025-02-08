package team.lindo.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import team.lindo.backend.application.entity.Clothing;
import team.lindo.backend.application.entity.Clothing.Category;
import team.lindo.backend.application.repository.ClothingRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClothingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        clothingRepository.deleteAll();
    }

    @Test
    public void testGetClothesByCategory() throws Exception {
        // 카테고리별 옷을 조회하기 전에 데이터를 먼저 추가합니다.
        Clothing clothing1 = new Clothing(null, "https://example.com/image1.jpg", "T-Shirt", 25000, Category.TOP);
        Clothing clothing2 = new Clothing(null, "https://example.com/image2.jpg", "Jeans", 40000, Category.BOTTOM);
        clothingRepository.save(clothing1);
        clothingRepository.save(clothing2);

        // TOP 카테고리로 옷을 조회
        mockMvc.perform(get("/api/clothes/category/TOP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("T-Shirt"));

        // BOTTOM 카테고리로 옷을 조회
        mockMvc.perform(get("/api/clothes/category/BOTTOM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jeans"));
    }
}
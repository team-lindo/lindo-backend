package team.lindo.backend.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.business.service.ClothingService;
import team.lindo.backend.application.entity.Clothing;

import java.util.List;

@RestController
@RequestMapping("")
public class ClothingController {

    private final ClothingService clothingService;

    public ClothingController(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    // 옷 추가
    @PostMapping
    public ResponseEntity<Clothing> addClothing(@RequestBody Clothing clothing) {
        Clothing savedClothing = clothingService.saveClothing(clothing);
        return ResponseEntity.ok(savedClothing);
    }

    // 모든 옷 조회
    @GetMapping
    public ResponseEntity<List<Clothing>> getAllClothes() {
        List<Clothing> clothes = clothingService.getAllClothes();
        return ResponseEntity.ok(clothes);
    }

    // 카테고리별 옷 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Clothing>> getClothesByCategory(@PathVariable Clothing.Category category) {
        List<Clothing> clothes = clothingService.getClothesByCategory(category);
        return ResponseEntity.ok(clothes);
    }

    //특정 옷 상세 정보를 조회
    @GetMapping("/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable Long id) {
        return ResponseEntity.ok(clothingService.getClothingById(id));
    }
}

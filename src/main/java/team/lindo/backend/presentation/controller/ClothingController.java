package team.lindo.backend.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.lindo.backend.application.business.service.ClothingService;
import team.lindo.backend.application.entity.Clothing;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clothes")
public class ClothingController {

    private final ClothingService clothingService;

    @Autowired
    public ClothingController(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    // 옷 등록 api
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Clothing  addClothing(@RequestBody Clothing clothing) {
        return clothingService.addClothing(clothing);
    }

    // 모든 옷 조회 api
    @GetMapping
    public List<Clothing> getAllClothes() {
        return clothingService.getAllClothes();
    }

    //id로 옷 조회 api
    @GetMapping("/{id}")
    public Clothing getClothingById(@PathVariable Long id) {
        Optional<Clothing> clothing = clothingService.getClothingById(id);
        return clothing.orElseThrow(() -> new RuntimeException("옷을 찾을 수 없습니다."));
    }

    // 카테고리별 옷 조회 api
    @GetMapping("/category/{category}")
    public List<Clothing> getClothesByCategory(@PathVariable Clothing.Category category) {
        return clothingService.getClothesByCategory(category);
    }

    // 옷 수정 api
    @PutMapping("/{id}")
    public Clothing updateClothing(@PathVariable Long id, @RequestBody Clothing clothing) {
        return clothingService.updateClothing(id, clothing);
    }

    // 옷 삭제 api
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClothing(@PathVariable Long id) {
        clothingService.deleteClothing(id);
    }
}

package team.lindo.backend.application.business.service;

import org.springframework.stereotype.Service;
import team.lindo.backend.application.Repository.ClothingRepository;
import team.lindo.backend.application.entity.Clothing;

import java.util.List;

@Service
public class ClothingService {
    private final ClothingRepository clothingRepository;

    public ClothingService(ClothingRepository clothingRepository){
        this.clothingRepository = clothingRepository;
    }

    // 옷 저장 기능
    public Clothing saveClothing(Clothing clothing) {
        return clothingRepository.save(clothing);
    }

    // 모든 옷 조회 기능
    public List<Clothing> getAllClothes() {
        return clothingRepository.findAll();
    }

    // 카테고리별 옷 조회 기능
    public List<Clothing> getClothesByCategory(Clothing.Category category) {
        return clothingRepository.findByCategory(category);
    }

    // 특정 옷 상세 조회 기능
    public Clothing getClothingById(Long id) {
        return clothingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옷을 찾을 수 없습니다."));
    }
}

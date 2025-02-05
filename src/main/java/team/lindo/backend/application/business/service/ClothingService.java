package team.lindo.backend.application.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.lindo.backend.application.repository.ClothingRepository;
import team.lindo.backend.application.entity.Clothing;

import java.util.List;
import java.util.Optional;

@Service
public class ClothingService {

    private final ClothingRepository clothingRepository;

    @Autowired
    public ClothingService(ClothingRepository clothingRepository){
        this.clothingRepository = clothingRepository;
    }

    // 옷 저장 기능
    public Clothing addClothing(Clothing clothing) {
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

    // id로 옷 조회
    public Optional<Clothing> getClothingById(Long id) {
        return clothingRepository.findById(id);
    }

    // 옷 정보 수정
    public Clothing updateClothing(Long id, Clothing clothingDetails) {
        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clothing not found"));

        clothing.setName(clothingDetails.getName());
        clothing.setImageUrl(clothingDetails.getImageUrl());
        clothing.setPrice(clothingDetails.getPrice());
        clothing.setCategory(clothingDetails.getCategory());

        return clothingRepository.save(clothing);
    }

    // 옷 삭제
    public void deleteClothing(Long id) {
        clothingRepository.deleteById(id);
    }
}

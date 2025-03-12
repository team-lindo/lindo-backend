package team.lindo.backend.application.wardrobe.dto;

import lombok.*;
import team.lindo.backend.application.product.entity.Category;
import team.lindo.backend.application.wardrobe.entity.WardrobeProduct;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardrobeProductDto  {
    private Long wardrobeId;
    private Long productId;
    private String productName;
    private String imageUrl; // 옷 사진 URL
    private String categoryName;

}

package team.lindo.backend.application.product.dto;

import lombok.Builder;
import lombok.Getter;
import team.lindo.backend.application.product.entity.Product;

@Getter
@Builder
public class ProductSearchDto {
    private Long id;
    private String name;
    private String brand;
    private String category;
    private String imageUrl;
    private Long categoryId;

    public static ProductSearchDto from(Product p) {
        return ProductSearchDto.builder()
                .id(p.getId())
                .name(p.getName())
                .brand(p.getBrand())
                .category(p.getCategory().getName())
                .categoryId(p.getCategory().getId())
                .imageUrl(p.getImageUrl())
                .build();
    }
}

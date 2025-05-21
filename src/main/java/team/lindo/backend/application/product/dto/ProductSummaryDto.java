package team.lindo.backend.application.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.lindo.backend.application.product.entity.Product;

@Getter
@AllArgsConstructor
public class ProductSummaryDto {
    private String uid;         // product.getId().toString()
    private String thumbnail;

    public ProductSummaryDto(Product product) {
        this.uid = product.getId().toString();
        this.thumbnail = product.getImageUrl();
    }
}

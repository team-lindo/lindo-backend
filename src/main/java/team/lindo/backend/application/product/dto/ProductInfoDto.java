package team.lindo.backend.application.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.product.entity.Product;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfoDto {
    String uid;
    String productName;
    String category;
    String brand;
    Double price;
    String thumbnail;  // 이미지 경로

    public ProductInfoDto(Product product) {
        this.uid = product.getId().toString();
        this.productName = product.getName();
        this.category = product.getCategory().getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.thumbnail = product.getImageUrl();
    }
}

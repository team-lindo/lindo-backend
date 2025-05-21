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
public class ProductDto {
    private String uid;           // = product.getId().toString()
    private String productName;
    private String category;
    private String brand;
    private Double price;
    private String thumbnail;

    public ProductDto(Product product) {
        this.uid = product.getId().toString();
        this.productName = product.getName();
        this.category = product.getCategory().getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.thumbnail = product.getImageUrl();
    }

}

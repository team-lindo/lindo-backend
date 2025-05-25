package team.lindo.backend.application.wardrobe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.wardrobe.entity.WardrobeProduct;

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

    public ProductDto(WardrobeProduct wp) {
        this.uid = wp.getId().toString();
        this.productName = wp.getProduct().getName();
        this.category = wp.getCategory().getName();
        this.brand = wp.getProduct().getBrand();
        this.price = wp.getProduct().getPrice();
        this.thumbnail = wp.getProduct().getImageUrl();
    }

}

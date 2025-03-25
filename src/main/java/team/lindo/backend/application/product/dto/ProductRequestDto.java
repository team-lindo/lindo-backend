package team.lindo.backend.application.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.product.entity.Category;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.entity.ProductCategory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {  //! 이 DTO 내용들로는 Product Entity 못 만듦!!!
    private String name;
    private String category;  // 최상위 카테고리 (카테고리가 여러 개 혹은 계층적일 수도)
    private String brand;
    private Double price;
//    private String size;  //! api에서 제공X여서 일단 주석 처리함
    private String siteUrl;  // 구매 링크
//    private String description;  //! api 제공 X + 필요없을 것 같음
    private String imageUrl;
    private String gender;

    public ProductRequestDto(Product product) {
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.price = product.getPrice();
       // this.size = product.getSize();
        this.brand = product.getBrand();
        /*this.category = product.getCategories().stream()
                .findFirst()  // 해당 제품이 속한 여러 카테고리 중 맨 처음 것 뽑는 형식 (특별한 기준 X)
                .map(Category::findTopLevelCategory)  // 해당 카테고리의 최상위 카테고리 (ex. 구두 -> 신발)
                .map(Category::getName)
                .orElse(null);*/
        this.category = product.getCategory().getName();
        this.gender = product.getGender();
    }
}

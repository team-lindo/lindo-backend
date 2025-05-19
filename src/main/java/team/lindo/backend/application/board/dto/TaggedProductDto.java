package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaggedProductDto {
    private Long productId;
    private String name;     // 상품 이름
    private Double price;// 상품 가격
    private double x;
    private double y;
}

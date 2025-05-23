package team.lindo.backend.application.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.lindo.backend.application.board.dto.PostDto;
import team.lindo.backend.application.product.dto.ProductSummaryDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchResponseDto {
    private List<ProductSummaryDto> products;
    private List<PostDto> hashtags;
}

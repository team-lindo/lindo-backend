package team.lindo.backend.application.wardrobe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FetchClosetResponseDto {
    private List<ProductDto> closetItems;
}

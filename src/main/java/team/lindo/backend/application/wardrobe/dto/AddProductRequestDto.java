package team.lindo.backend.application.wardrobe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequestDto {
    private String productName;
    private String category;
    private String brand;
}

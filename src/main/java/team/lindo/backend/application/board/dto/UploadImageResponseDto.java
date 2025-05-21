package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadImageResponseDto {
    private String imageId;   // 이미지 식별자 (예: UUID 또는 DB ID)
    private String imageUrl;  // 저장된 이미지 URL
}
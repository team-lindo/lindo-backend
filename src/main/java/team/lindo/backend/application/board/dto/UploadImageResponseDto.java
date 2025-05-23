package team.lindo.backend.application.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadImageResponseDto {
    private String id;   // 이미지 식별자 (예: UUID 또는 DB ID)
    private String url;  // 저장된 이미지 URL
}
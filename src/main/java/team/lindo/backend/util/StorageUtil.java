package team.lindo.backend.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class StorageUtil {

    private final String uploadDir = "/uploads"; // 또는 외부 설정값

    public String save(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);
            return "/uploads/" + fileName; // 실제 URL은 CDN or 프론트 경로에 맞게
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}

package team.lindo.backend.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class StorageUtil {

    private final String uploadDir = "./uploads";  // 상대 경로 또는 절대 경로 가능

    public StorageUtil() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();  // 경로가 없으면 자동 생성
        }
    }

    public String save(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}

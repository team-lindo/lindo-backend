package team.lindo.backend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import team.lindo.backend.application.user.security.CustomUserDetails;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        if(!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("유효하지 않은 사용자 정보입니다.");
        }

        return ((CustomUserDetails)principal).getId();
    }
}
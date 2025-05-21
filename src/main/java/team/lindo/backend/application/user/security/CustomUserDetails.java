package team.lindo.backend.application.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.lindo.backend.application.user.entity.User;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {  // 사용자 정보를 담는 인터페이스 -> 사용자 정보 구현
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {  // 사용자의 권한 반환 (role값)
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {  // 중복 X로 설정 필요 (ex. User의 email 필드 @Column(unique=true) 설정)
        return user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

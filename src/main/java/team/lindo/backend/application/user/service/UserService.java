package team.lindo.backend.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.user.dto.SignUpRequestDto;
import team.lindo.backend.application.user.entity.Role;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 생성
    public User createUser(User user) {
        return userRepository.save(user);
    }

    //! createUser 메서드 없애고 이거 사용해야 하나???
    public void registerUser(SignUpRequestDto request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        userRepository.save(User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getRawPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .build()
        );
    }

    // 사용자 조회
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }

    // 사용자 정보 수정
    @Transactional  //! 메서드 좀 더 세세하게 분할 필요? (ex. 이름 수정 메서드, 이메일 수정, 비밀번호 수정, ... 분할?)
    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        return user.updateNickname(updatedUser.getNickname())
                .updateEmail(updatedUser.getEmail());
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

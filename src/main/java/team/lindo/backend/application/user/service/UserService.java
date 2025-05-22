package team.lindo.backend.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.board.dto.PostDto;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.common.exception.UserAlreadyExistsException;
import team.lindo.backend.application.social.entity.Follow;
import team.lindo.backend.application.social.repository.follow.FollowRepository;
import team.lindo.backend.application.social.service.FollowService;
import team.lindo.backend.application.user.dto.*;
import team.lindo.backend.application.user.entity.QUser;
import team.lindo.backend.application.user.entity.Role;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;
import team.lindo.backend.application.wardrobe.entity.Wardrobe;
import team.lindo.backend.application.wardrobe.repository.WardrobeRepository;

import java.util.List;

import static team.lindo.backend.application.user.entity.QUser.user;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostingRepository postingRepository;
    private final FollowRepository followRepository;
    private final WardrobeRepository wardrobeRepository;
    private final FollowService followService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;  //! JWT 토큰 발급 관련. 추후 필요하면 추가?

    // 사용자 생성
    public User createUser(User user) {
        return userRepository.save(user);
    }

    //! createUser 메서드 없애고 이거 사용해야 하나???
    public UserSummaryDto registerUser(SignUpRequestDto request) {  //! return void로 하는 게 낫나?
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }

        User registeredUser = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getRawPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .build()
        );

        wardrobeRepository.save(Wardrobe.builder()
                .user(registeredUser)
                .build()
        );


        return new UserSummaryDto(registeredUser);
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

    public UserSummaryDto login(LoginRequestDto loginRequest) {
        // 사용자 인증 (인증 성공시 Authentication 객체 return, 인증된 사용자 정보 Security Context에 저장됨)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        List<Follow> followings = followRepository.findByFollowerId(user.getId());
        List<Follow> followers = followRepository.findByFollowingId(user.getId());

//        String token = jwtTokenProvider.generateToken(user);  //! JWT 토큰 발급

        return new UserSummaryDto(user);
    }

    public LogoutResponseDto logout() {  //! JWT 기반 인증으로 수정 시 토큰을 삭제하는 방식으로 바꿔야 함
        SecurityContextHolder.clearContext();
        return new LogoutResponseDto("로그아웃 되었습니다.");
    }

    public UserProfileDto loadMyUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Long followingsCount = followService.getFollowingCount(id);
        Long followersCount = followService.getFollowerCount(id);
        Long postsCount = postingRepository.countByUserId(id);

        List<PostDto> posts = postingRepository.findByUserId(id).stream()
                .map(PostDto::new)
                .toList();

        return UserProfileDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .followingsCount(followingsCount)
                .followersCount(followersCount)
                .postsCount(postsCount)
                .posts(posts)
                .build();
    }

    public FetchUserProfileDto loadUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Long postsCount = postingRepository.countByUserId(id);

        List<PostDto> posts = postingRepository.findByUserId(id).stream()
                .map(PostDto::new)
                .toList();

        return FetchUserProfileDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .postsCount(postsCount)
                .posts(posts)
                .build();
    }
}

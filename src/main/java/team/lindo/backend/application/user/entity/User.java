package team.lindo.backend.application.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.lindo.backend.application.common.entity.BaseEntity;
import team.lindo.backend.application.wardrobe.entity.Wardrobe;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "User")
public class User extends BaseEntity {  // BaseEntity 필요 없나?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = )
//    private LogInfo logInfo;  // 반대편에 @OneToOne(mappedBy="logInfo")

//    @Column(name = "username", nullable = false, unique = true)
//    private String username;  //! 아이디 -> 이메일 로그인 방식이니 필요 X?

    @Column(name = "nickname", nullable = false)
    private String nickname;  // 사용자명

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;  // 반드시 암호화된 비밀번호를 저장해야 함

    @Enumerated(EnumType.STRING)
    private Role role;

    /*@Column(name = "profileImageUrl")
    private String profileImageUrl;*/

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Wardrobe wardrobe;

    //! List<Posting>, List<Comment>, List<Like>, List<Follow> 같은 것들은 필드로 두지 말고 query로 해결?

//    public User updateName(String username) {  //! username도 변경 가능???
//        if(username != null && !username.isBlank()) {
//            this.username = username;
//        }
//        return this;
//    }

    public User updateNickname(String nickname) {
        if(nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        return this;
    }

    public User updateEmail(String email) {
        if(email != null && !email.isBlank()) {
            this.email = email;
        }
        return this;
    }

    /*public User updateProfileImageUrl(String profileImageUrl) {
        if(profileImageUrl != null && !profileImageUrl.isBlank()) {
            this.profileImageUrl = profileImageUrl;
        }
        return this;
    }*/

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}

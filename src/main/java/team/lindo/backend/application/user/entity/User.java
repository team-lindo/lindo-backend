package team.lindo.backend.application.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.lindo.backend.application.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {  // BaseEntity 필요 없나?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = )
//    private LogInfo logInfo;  // 반대편에 @OneToOne(mappedBy="logInfo")

    private String name;

    private String nickName;

    private String email;

//    @OneToOne
//    @JoinColumn(name = )
//    private Closet closet;

    // List<Posting>, List<Comment>, List<Like>, List<Follow> 같은 것들은 필드로 두지 말고 query로 해결?

    public User updateName(String name) {
        if(name != null && !name.isBlank()) {
            this.name = name;
        }
        return this;
    }

    public User updateNickName(String nickName) {
        if(nickName != null && !nickName.isBlank()) {
            this.nickName = nickName;
        }
        return this;
    }

    public User updateEmail(String email) {
        if(email != null && !email.isBlank()) {
            this.email = email;
        }
        return this;
    }
}

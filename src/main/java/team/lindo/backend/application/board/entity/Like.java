package team.lindo.backend.application.board.entity;

import jakarta.persistence.*;
import lombok.*;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.common.entity.BaseEntity;
import team.lindo.backend.application.user.entity.User;

@Entity
@Table(name = "Likes")  // Like는 MySQL 예약어여서 그대로 사용 불가
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Table(name = "likes", uniqueConstraints = {
//        @UniqueConstraint(name = "unique_user_posting_like", columnNames = {"user_id", "posting_id"})
//})  // DB 레벨에서 user_id와 posting_id가 중복되는 행을 방지 -> 한 사용자가 같은 게시물에 여러 번 좋아요를 누르는 것을 방지
//@EqualsAndHashCode(of = {"user", "posting"})  // 동등성 비교에서 user와 posting만 비교. (id는 DB에서 자동생성하므로 제외시키기)
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;
}

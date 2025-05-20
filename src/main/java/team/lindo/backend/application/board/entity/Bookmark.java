package team.lindo.backend.application.board.entity;


import jakarta.persistence.*;
import lombok.*;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookmark")
public class Bookmark extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    public Bookmark(User user, Posting posting) {
        this.user = user;
        this.posting = posting;
    }
}
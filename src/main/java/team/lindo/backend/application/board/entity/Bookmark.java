package team.lindo.backend.application.board.entity;


import jakarta.persistence.*;
import lombok.*;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.board.entity.Posting;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookmark_table ")
public class Bookmark {

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
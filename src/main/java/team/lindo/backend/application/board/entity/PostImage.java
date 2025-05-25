package team.lindo.backend.application.board.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;
import team.lindo.backend.application.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PostImage")
public class PostImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String imageUrl;  // S3 or 서버 경로

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    private Posting posting;

}

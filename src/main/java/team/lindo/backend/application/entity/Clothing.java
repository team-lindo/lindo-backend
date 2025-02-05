package team.lindo.backend.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category {
        OUTER, TOP, BOTTOM, SHOES, ACCESSORY
    }
}

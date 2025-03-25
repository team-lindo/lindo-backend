package team.lindo.backend.application.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // 부모 카테고리를 참조
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>(); // 자식 카테고리 목록

    private int depth; // 카테고리 깊이

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductCategory> productCategories = new HashSet<>();

    public Category findTopLevelCategory() {
        Category current = this;
        while(current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    public Category getRootCategory() {
        Category current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }*/
}

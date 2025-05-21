package team.lindo.backend.application.board.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostingProductId implements Serializable { // JPA에서 복합 키 클래스는 반드시 구현해야함.
    private Long postingId;
    private Long productId;

    // equals, hashCode 구현 필수 -> equals -> JPA entity 동등성 비교, hashCode -> 컬렉션에서 객체 고유성 판단
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostingProductId that = (PostingProductId) o;
        return Objects.equals(getPostingId(), that.getPostingId()) && Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostingId(), getProductId());
    }
}

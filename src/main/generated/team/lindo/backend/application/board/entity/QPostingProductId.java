package team.lindo.backend.application.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostingProductId is a Querydsl query type for PostingProductId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostingProductId extends BeanPath<PostingProductId> {

    private static final long serialVersionUID = 1085645552L;

    public static final QPostingProductId postingProductId = new QPostingProductId("postingProductId");

    public final NumberPath<Long> postingId = createNumber("postingId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QPostingProductId(String variable) {
        super(PostingProductId.class, forVariable(variable));
    }

    public QPostingProductId(Path<? extends PostingProductId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostingProductId(PathMetadata metadata) {
        super(PostingProductId.class, metadata);
    }

}

